package com.finki.bank.service.impl;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.AccountStatusType;
import com.finki.bank.domain.enumerations.Currency;
import com.finki.bank.repository.AccountRepository;
import com.finki.bank.repository.UserRepository;
import com.finki.bank.security.CurrentUserService;
import com.finki.bank.service.AccountService;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.exceptions.AccountAlreadyExistsException;
import com.finki.bank.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, UserRepository userRepository, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account registerAccount = accountMapper.convertToAccount(accountDto);

        User ownerOfAccount = currentUserService.getUser();

//        if(!ownerOfAccount.getAccounts().isEmpty()){
//            throw new AccountAlreadyExistsException();
//        }
        Currency accCurrency = registerAccount.getCurrency();

        if(ownerOfAccount.getAccounts()
                .stream()
                .anyMatch( acc -> accCurrency.equals(acc.getCurrency()))){
            throw new AccountAlreadyExistsException();
        }

        registerAccount.setBalance(new BigDecimal("0.00"));
        registerAccount.setStatus(AccountStatusType.ACTIVE);


//        Account existingAccount = accountRepository.findById(accountDto.getId()).orElseThrow(EntityNotFoundException::new);
        //TODO: sredi
//        User owner = userRepository.findById(accountDto.getOwnerId()).orElseThrow(EntityNotFoundException::new);
//                .orElseThrow( () -> new EntityNotFoundException());
        registerAccount.setOwner(ownerOfAccount);


        registerAccount = accountRepository.save(registerAccount);
        return accountMapper.convertToDto(registerAccount);

    }

//    @Override
//    public List<AccountDto> findAll() {
//        return accountRepository.findAll().stream()
//                .map(accountMapper::convertToDto)
////                .map(x -> accountMapper.convertToDto(x))
//                .collect(Collectors.toCollection(LinkedList::new));
//    }

    @Override
    public AccountDto updateStatus(String status,Long id){
        if(id == null || accountRepository.findById(id).isEmpty())
            throw new RuntimeException();

        //TODO: proveri ata dali e dobro i dali mozi so pomalce povici do baza
        Account accStatus = accountRepository.findById(id).get();

        if(status.equals(AccountStatusType.ACTIVE.toString()))
            accStatus.setStatus(AccountStatusType.DISABLED);
        else
            accStatus.setStatus(AccountStatusType.ACTIVE);

        accStatus = accountRepository.save(accStatus);

        return accountMapper.convertToDto(accStatus);

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