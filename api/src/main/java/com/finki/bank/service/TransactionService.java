package com.finki.bank.service;

import com.finki.bank.service.dto.ResultTransactionDto;
import com.finki.bank.service.dto.TransactionDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    ResultTransactionDto save(TransactionDto transactionDto);

    //List<ResultTransactionDto> search(LocalDate startDate, LocalDate endDate, Long id);

    Page<ResultTransactionDto> searchPageable(Pageable pageable,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              Long id,
                                              BigDecimal startAmount,
                                              BigDecimal endAmount);

    String exportReport(List<ResultTransactionDto> transactions) throws FileNotFoundException, JRException;


    void exportReport(List<ResultTransactionDto> transactions, OutputStream outputStream) throws FileNotFoundException, JRException;

}
