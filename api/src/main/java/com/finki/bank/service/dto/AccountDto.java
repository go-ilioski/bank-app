package com.finki.bank.service.dto;

import com.finki.bank.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDto implements Serializable {

    private Long id;

    private BigDecimal balance;

    private String currency;

    private String status;

    private String owner;

    private Long ownerId;
}
