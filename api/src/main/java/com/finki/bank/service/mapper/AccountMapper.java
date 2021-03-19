package com.finki.bank.service.mapper;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.User;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {


    public AccountDto convertToDto(Account account){

        ModelMapper mapper = new ModelMapper();
        AccountDto accountDto = mapper.map(account,AccountDto.class);

        return accountDto;
    }

    public Account convertToAccount(AccountDto accountDto){

        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(accountDto, Account.class);
        return account;
    }



}
