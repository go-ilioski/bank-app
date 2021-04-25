package com.finki.bank.service.impl;

import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.repository.UserRepository;
import com.finki.bank.security.CurrentUserService;
import com.finki.bank.service.UserService;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;
import com.finki.bank.service.exceptions.EmailAlreadyUsedException;
import com.finki.bank.service.mapper.AccountMapper;
import com.finki.bank.service.mapper.UserMapper;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
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
    private final AccountMapper accountMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, CurrentUserService currentUserService, AccountMapper accountMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        //this.cacheManager = cacheManager;
        this.userMapper = userMapper;
        this.currentUserService = currentUserService;
        this.accountMapper = accountMapper;
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

    public UserDto updateUser(UserDto userDto) {
        User user = currentUserService.getUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return userMapper.convertToDto(user);
    }

    public List<AccountDto> listUserAccounts(){
        if(currentUserService.getUserId() == null){
            throw new RuntimeException();
        }
        //TODO: sto ako e prazna listata
        return accountMapper.convertToDto(currentUserService.getUser().getAccounts());

    }

    @Override
    public List<UserPublicDetailsDto> listAllMerchants() {
        return userMapper.convertToUserPublicDetailsDtos(userRepository.findAllByRole(Role.MERCHANT));
    }

    @Override
    public List<UserPublicDetailsDto> addFavouriteUser(Long favoriteUserId) {
        User currentUser = currentUserService.getUser();
        boolean userAlreadyFavorite = currentUser.getFavouriteUsers().stream()
                .anyMatch(user -> user.getId().equals(favoriteUserId));
        if (userAlreadyFavorite) {
            throw new BadRequestAlertException("User already a favorite.");
        }
        User favoriteUser = userRepository.findById(favoriteUserId).orElseThrow(EntityNotFoundException::new);
        currentUser.getFavouriteUsers().add(favoriteUser);
        userRepository.save(currentUser);
        return userMapper.convertToUserPublicDetailsDtos(currentUser.getFavouriteUsers());
    }

    @Override
    public void removeFavouriteUser(Long favoriteUserId) {
        User currentUser = currentUserService.getUser();
        User favoriteUserToRemove = currentUser.getFavouriteUsers().stream()
                .filter(user -> user.getId().equals(favoriteUserId))
                .findFirst().orElse(null);
        if (favoriteUserToRemove == null) {
            throw new BadRequestAlertException("User does not exist as favorite.");
        }
        currentUser.getFavouriteUsers().remove(favoriteUserToRemove);
        userRepository.save(currentUser);
    }

    @Override
    public List<UserPublicDetailsDto> findAllFavouriteUsers() {
        User currentUser = currentUserService.getUser();
        User user = userRepository.findOneWithEagerRelationships(currentUser.getId()).orElseThrow(EntityNotFoundException::new);
        return userMapper.convertToUserPublicDetailsDtos(user.getFavouriteUsers());
    }


    @Override
    public Optional<UserDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
