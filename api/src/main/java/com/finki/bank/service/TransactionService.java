package com.finki.bank.service;

import com.finki.bank.service.dto.ResultTransactionDto;
import com.finki.bank.service.dto.TransactionDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    ResultTransactionDto save(TransactionDto transactionDto);

    List<ResultTransactionDto> search(LocalDate startDate, LocalDate endDate, Long id);


}
