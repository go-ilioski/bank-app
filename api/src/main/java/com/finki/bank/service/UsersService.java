package com.finki.bank.service;

import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.repository.UserRepository;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.exceptions.EmailAlreadyUsedException;
import com.finki.bank.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        //this.cacheManager = cacheManager;
        this.userMapper = userMapper;
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

}
