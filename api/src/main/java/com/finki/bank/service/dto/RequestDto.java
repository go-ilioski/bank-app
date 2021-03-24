package com.finki.bank.service.dto;

import com.finki.bank.domain.Account;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RequestDto implements Serializable {

    private Long id;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String description;

    private String type;

    private AccountDto fromAccount;

    private AccountDto toAccount;
}
