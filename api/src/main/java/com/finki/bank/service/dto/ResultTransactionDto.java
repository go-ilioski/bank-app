package com.finki.bank.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResultTransactionDto extends TransactionDto implements Serializable {
    private BigDecimal commission;

    private String fromAccountOwnerEmail;

    private String toAccountOwnerEmail;

    private LocalDateTime createdDate;
}
