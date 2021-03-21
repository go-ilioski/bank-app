package com.finki.bank.service.impl;

import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.repository.UserRepository;
import com.finki.bank.security.CurrentUserService;
import com.finki.bank.service.UserService;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;
import com.finki.bank.service.exceptions.EmailAlreadyUsedException;
import com.finki.bank.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CurrentUserService currentUserService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        //this.cacheManager = cacheManager;
        this.userMapper = userMapper;
        this.currentUserService = currentUserService;
    }

    public UserDto registerUser(RegisterUserDto userDTO) {
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        //newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());

        // new user is not active
        // TODO: set to active false, and set activation key
        newUser.setActivated(true);
        // new user gets registration key
//        newUser.setActivationKey(RandomUtil.generateActivationKey());
        newUser.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        User createdUser = userRepository.save(newUser);
        return userMapper.convertToDto(createdUser);
    }


    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        //this.clearUserCaches(existingUser);
        return true;
    }

    public List<UserPublicDetailsDto> userSearch(String search) {
        User user = currentUserService.getUser();
        return userMapper
                .convertToUserPublicDetailsDtos(userRepository.findAllByEmailStartsWithIgnoreCase(search));
    }


    @Override
    public List<UserPublicDetailsDto> addFavouriteUser(Long favoriteUserId) {
        User currentUser = currentUserService.getUser();
        User favoriteUser = userRepository.findById(favoriteUserId).orElseThrow(EntityNotFoundException::new);
        currentUser.getFavouriteUsers().add(favoriteUser);
        userRepository.save(currentUser);
        return userMapper.convertToUserPublicDetailsDtos(currentUser.getFavouriteUsers());
    }

    @Override
    public List<UserPublicDetailsDto> findAllFavouriteUsers(Long userId) {
        User user = userRepository.findOneWithEagerRelationships(userId).orElseThrow(EntityNotFoundException::new);
        return userMapper.convertToUserPublicDetailsDtos(user.getFavouriteUsers());
//                .stream()
//                .map(usersMapper::toDto)
//                .collect(Collectors.toCollection(LinkedList::new));
    }

//    @Override
//    public Page<UserDto> findAllWithEagerRelationships(Pageable pageable) {
//        return null;
//    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
