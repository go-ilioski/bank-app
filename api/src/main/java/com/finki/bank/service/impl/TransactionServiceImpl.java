package com.finki.bank.service.impl;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.Transaction;
import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.AccountStatusType;
import com.finki.bank.domain.enumerations.TransactionStatus;
import com.finki.bank.domain.enumerations.TransactionType;
import com.finki.bank.repository.AccountRepository;
import com.finki.bank.repository.TransactionRepository;
import com.finki.bank.security.CurrentUserService;
import com.finki.bank.service.AccountService;
import com.finki.bank.service.TransactionService;
import com.finki.bank.service.dto.TransactionDto;
import com.finki.bank.service.exceptions.TransactionException;
import com.finki.bank.service.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountService accountService, AccountRepository accountRepository, CurrentUserService currentUserService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public TransactionDto save(TransactionDto transactionDto) {
        //log.debug("Request to save Transactions : {}", transactionsDTO);
        Transaction transaction = transactionMapper.convertToTransaction(transactionDto);
        User currentUser = currentUserService.getUser();
        Optional<Account> toAccount = Optional.empty();
        if (transactionDto.getToAccountId() != null) {
            toAccount = accountRepository.findById(transactionDto.getToAccountId());
        }
        Optional<Account> fromAccount = Optional.empty();
        if (transactionDto.getFromAccountId() != null) {
            fromAccount = accountRepository.findById(transactionDto.getFromAccountId());
        }

        TransactionType transactionType = transaction.getType();

        switch (transactionType){
            case DEPOSIT:
                transaction = depositTransaction(transaction,currentUser,toAccount);
                break;

            case PAYMENT:

                break;

            case WITHDRAW:
                transaction = withdrawTransaction(transaction,currentUser,fromAccount);
                break;

        }




        transaction = transactionRepository.save(transaction);
        return transactionMapper.convertToDto(transaction);
    }

    private Transaction depositTransaction(Transaction transaction, User currentUser, Optional<Account> optionalToAccount){
       // if (optionalToAccount.get().getOwner().getId().equals(currentUser.getId()))
        if(optionalToAccount.isEmpty()){
            throw new EntityNotFoundException();
        }
        Account toAccount = optionalToAccount.get();

        if(!toAccount.getOwner().equals(currentUser)){
            throw new TransactionException("owner does not have this acc!!");
        }
        if(!AccountStatusType.ACTIVE.equals(toAccount.getStatus())){
            throw new TransactionException("Your account is not active. Please activate your account to proceed.");
        }

        if(!toAccount.getCurrency().equals(transaction.getCurrency())){
            throw new TransactionException("Your currency does not match.");
        }

        BigDecimal commision = getCommision(transaction);
        //to acc samo id od dto
        transaction.setCommission(commision);

        addFundsToAccount(toAccount,transaction);
        transaction.setToAccount(toAccount);
        accountRepository.save(toAccount);

        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        return transaction;
    }



    private Transaction withdrawTransaction(Transaction transaction, User currentUser ,Optional<Account> optionalFromAccount){
        if(optionalFromAccount.isEmpty()){
            throw new EntityNotFoundException();
        }
        Account fromAccount = optionalFromAccount.get();

        if(!fromAccount.getOwner().equals(currentUser)){
            throw new TransactionException("owner does not have this acc!!");
        }
        if(!AccountStatusType.ACTIVE.equals(fromAccount.getStatus())){
            throw new TransactionException("Your account is not active. Please activate your account to proceed.");
        }

        if(!fromAccount.getCurrency().equals(transaction.getCurrency())){
            throw new TransactionException("Your currency does not match.");
        }

        BigDecimal commision = getCommision(transaction);
        transaction.setCommission(commision);

        subtractFundsFromAccount(fromAccount, transaction);
        transaction.setFromAccount(fromAccount);
        accountRepository.save(fromAccount);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);

        return transaction;


    }
    private Transaction paymentTransaction(Transaction transaction){
        return null;
    }

    private BigDecimal getCommision(Transaction transaction) {
        return transaction.getAmount()
                .multiply(transaction.getType().getCommissionRate())
                .divide(new BigDecimal("100.00"), RoundingMode.HALF_EVEN);
    }

    private void addFundsToAccount(Account account, Transaction transaction) {
        account.setBalance(account.getBalance().add(transaction.getAmount().add(transaction.getCommission())));
    }

    private void subtractFundsFromAccount(Account account, Transaction transaction) {
        BigDecimal amountToSubtract = transaction.getAmount().add(transaction.getCommission());
        if (amountToSubtract.compareTo(account.getBalance()) > 0) {
            throw new TransactionException("Not enough account balance for transaction");
        }
        account.setBalance(account.getBalance().subtract(amountToSubtract));
    }




}
