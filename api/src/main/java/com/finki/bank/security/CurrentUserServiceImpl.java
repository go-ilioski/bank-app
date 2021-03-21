package com.finki.bank.security;

import com.finki.bank.domain.User;
import com.finki.bank.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserId() {
        return getUser().getId();

    }
//
//    public Set<Authority> getUserAuthorities() {
//        return getUser().getAuthorities();
//    }
//
//    public Set<String> getUserAuthoritiesStrings() {
//        return getUser().getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
//    }
//
//    public boolean isOwner() {
//        return getUserAuthoritiesStrings().contains(AuthoritiesConstants.RESTAURANT);
//    }
//
//    public boolean isMobileUser() {
//        return getUserAuthoritiesStrings().contains(AuthoritiesConstants.USER);
//    }
//
//    public boolean isAdmin() {
//        return getUserAuthoritiesStrings().contains(AuthoritiesConstants.ADMIN);
//    }

    public User getUser() {
        return getCurrentUserLogin()
            .flatMap(userRepository::findOneByEmailIgnoreCase)
            .orElseThrow(() -> new RuntimeException("Current user login not found"));
    }

//    public User getUserWithPreferences() {
//        return SecurityUtils.getCurrentUserLogin()
//            .flatMap(userRepository::findOneWithPreferencesByLogin)
//            .orElseThrow(() -> new RuntimeException("Current user login not found"));
//    }
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public boolean isCurrentUserAdminOrOwner(Long userId) {
//        return isAdmin() || userId.equals(getUserId());
//    }

    private static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
