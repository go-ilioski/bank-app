package com.finki.bank.service.dto;

import com.finki.bank.domain.Account;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TransactionDto implements Serializable {

    private Long id;

    private String type;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String description;

    private BigDecimal commission;

    private Long fromAccountId;

    private String fromAccountOwner;

    private Long toAccountId;

    private String toAccountOwner;

}
