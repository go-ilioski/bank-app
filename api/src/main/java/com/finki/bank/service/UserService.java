package com.finki.bank.service;

import com.finki.bank.domain.enumerations.Role;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.RegisterUserDto;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //UserDto save(UserDto userDto);

//    Page<UserDto> findAllWithEagerRelationships(Pageable pageable);

    Optional<UserDto> findOne(Long id);

    void delete(Long id);

    List<UserPublicDetailsDto> userSearch(String search);

    UserDto registerUser(RegisterUserDto userDTO);

    List<UserPublicDetailsDto> addFavouriteUser(Long favoriteUserId);

    List<UserPublicDetailsDto> findAllFavouriteUsers(Long userId);

    List<AccountDto> listUserAccounts();

    List<UserPublicDetailsDto> listAllMerchants();


}
