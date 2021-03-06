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
        User ownerOfAccount;
        if (accountDto.getOwnerId() == null) {
            ownerOfAccount = currentUserService.getUser();
        } else {
            ownerOfAccount = userRepository.findById(accountDto.getOwnerId()).orElseThrow(EntityNotFoundException::new);
        }
        return createAccount(accountDto, ownerOfAccount);

    }
//
//    @Override
//    public AccountDto save(AccountDto accountDto, Long ownerId) {
//
//        return createAccount(accountDto, ownerOfAccount);
//
//    }

    private AccountDto createAccount(AccountDto accountDto, User ownerOfAccount) {
        Account registerAccount = accountMapper.convertToAccount(accountDto);

//        if(!ownerOfAccount.getAccounts().isEmpty()){
//            throw new AccountAlreadyExistsException();
//        }
        Currency accCurrency = registerAccount.getCurrency();

        //dali veke ima acc so mkd ili eur -> ako ima stop
        if(ownerOfAccount.getAccounts()
                .stream()
                .anyMatch( acc -> accCurrency.equals(acc.getCurrency()))){
            throw new AccountAlreadyExistsException();
        }

        registerAccount.setBalance(new BigDecimal("0.00"));
        registerAccount.setStatus(AccountStatusType.ACTIVE);


//        Account existingAccount = accountRepository.findById(accountDto.getId()).orElseThrow(EntityNotFoundException::new);
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

//    public List<AccountDto> getAllMerchants(){
//        return accountMapper.convertToDto(accountRepository.findAccountsByOwner_Role_Merchant());
//    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}