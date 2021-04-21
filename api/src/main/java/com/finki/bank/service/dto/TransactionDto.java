package com.finki.bank.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.finki.bank.domain.Account;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto implements Serializable {
    private Long id;

    private String type;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String description;

    private Long fromAccountId;

    private Long toAccountId;

}
