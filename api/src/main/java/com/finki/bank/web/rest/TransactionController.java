package com.finki.bank.web.rest;

import com.finki.bank.service.TransactionService;
import com.finki.bank.service.dto.ResultTransactionDto;
import com.finki.bank.service.dto.TransactionDto;
import com.finki.bank.util.Constants;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<ResultTransactionDto> processTransaction(@RequestBody TransactionDto transactionDto){
        if(transactionDto.getId() != null){
            throw new BadRequestAlertException();
        }

        ResultTransactionDto newDepositTransaction = transactionService.save(transactionDto);

        return ResponseEntity.status(HttpStatus.OK).body(newDepositTransaction);

    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority(\"" + Constants.ADMIN_ROLE + "\")"
            + "|| hasAuthority(\"" + Constants.USER_ROLE + "\")" )
    public ResponseEntity<List<ResultTransactionDto>> getTransactions(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchStartDate,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate searchEndDate, Long id){
//        if(transactionDto.getId() != null){
//            throw new BadRequestAlertException();
//        }

        if(searchStartDate.isAfter(LocalDate.now()) || searchEndDate.isAfter(searchEndDate)){
            throw new BadRequestAlertException("Invalid Date input");
        }

        List<ResultTransactionDto> searchDateTransactions = transactionService.search(searchStartDate,searchEndDate,id);

        return ResponseEntity.status(HttpStatus.OK).body(searchDateTransactions);
    }
}
