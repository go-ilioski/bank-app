package com.finki.bank.service.mapper;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.Currency;
import com.finki.bank.service.dto.UserDto;
import com.finki.bank.service.dto.UserPublicDetailsDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {

    public UserDto convertToDto(User user){

        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(user,UserDto.class);
        return userDto;
    }

    public User convertToUser(UserDto userDto){
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDto,User.class);
        return user;
    }

    public UserPublicDetailsDto convertToUserPublicDetails(User user) {
        ModelMapper mapper = new ModelMapper();
        UserPublicDetailsDto userDto = mapper.map(user, UserPublicDetailsDto.class);
        Account defaultAccount = user.getAccounts()
                .stream()
                .filter(account -> account.getCurrency().equals(Currency.MKD))
                .findFirst()
                .orElse(null);
        userDto.setAccountId(defaultAccount != null ? defaultAccount.getId() : null);
        return userDto;
    }
    //??
    public List<UserDto> convertToDto(List<User> userList){
        return userList.stream().map(x -> convertToDto(x)).collect(Collectors.toList());
    }
    //??
    public List<User> convertToUser(List<UserDto> userDtos){
        return userDtos.stream().map(x -> convertToUser(x)).collect(Collectors.toList());
    }

    public List<UserPublicDetailsDto> convertToUserPublicDetailsDtos(List<User> userList){
        return userList.stream().map(x -> convertToUserPublicDetails(x)).collect(Collectors.toList());
    }

}
