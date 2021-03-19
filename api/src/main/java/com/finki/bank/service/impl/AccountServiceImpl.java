package com.finki.bank.service.impl;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.User;
import com.finki.bank.repository.AccountRepository;
import com.finki.bank.service.AccountService;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account account = accountMapper.convertToAccount(accountDto);


        Account existingAccount = accountRepository.findById(accountDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        //TODO: sredi
        User owner = userRepository.findByid(accountDto.getOwnerId())
                .orElseThrow( EntityNotFoundException);
        account.setOwner(owner);


        account = accountRepository.save(account);
        return accountMapper.convertToDto(account);

    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::convertToDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<AccountDto> findOne(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::convertToDto);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}