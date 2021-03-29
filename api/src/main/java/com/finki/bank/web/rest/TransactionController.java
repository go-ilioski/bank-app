package com.finki.bank.web.rest;

import com.finki.bank.service.TransactionService;
import com.finki.bank.service.dto.TransactionDto;
import com.finki.bank.util.Constants;
import com.finki.bank.web.rest.errors.BadRequestAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TransactionDto> processTransaction(@RequestBody TransactionDto transactionDto){
        if(transactionDto.getId() != null){
            throw new BadRequestAlertException();
        }

        TransactionDto newDepositTransaction = transactionService.save(transactionDto);

        return ResponseEntity.status(HttpStatus.OK).body(newDepositTransaction);

    }
}
