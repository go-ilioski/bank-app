package com.finki.bank.service.mapper;

import com.finki.bank.domain.Account;
import com.finki.bank.domain.Transaction;
import com.finki.bank.service.dto.AccountDto;
import com.finki.bank.service.dto.ResultTransactionDto;
import com.finki.bank.service.dto.TransactionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionDto convertToDto(Transaction transaction){

        ModelMapper mapper = new ModelMapper();
        TransactionDto transactionDto = mapper.map(transaction,TransactionDto.class);
        return transactionDto;
    }

    public ResultTransactionDto convertToResultDto(Transaction transaction) {

        ModelMapper mapper = new ModelMapper();
        ResultTransactionDto transactionDto = mapper.map(transaction,ResultTransactionDto.class);
        return transactionDto;
    }

    public Transaction convertToTransaction(TransactionDto transactionDto){

        ModelMapper mapper = new ModelMapper();
        Transaction transaction = mapper.map(transactionDto,Transaction.class);
        return transaction;
    }

    public List<TransactionDto> convertToDto(List<Transaction> transactionList){
        return transactionList.stream().map(x -> convertToDto(x)).collect(Collectors.toList());
    }

    public List<ResultTransactionDto> convertToResultDtos(List<Transaction> transactionList){
        return transactionList.stream().map(x -> convertToResultDto(x)).collect(Collectors.toList());
    }
}
