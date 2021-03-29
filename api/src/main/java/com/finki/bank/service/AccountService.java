package com.finki.bank.service;

import com.finki.bank.service.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    AccountDto save(AccountDto accountDto);

    //List<AccountDto> findAll();

    Optional<AccountDto> findOne(Long id);

    void delete(Long id);

    AccountDto updateStatus(String status,Long id);

}
