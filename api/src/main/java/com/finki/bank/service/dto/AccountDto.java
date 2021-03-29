package com.finki.bank.service.dto;

import com.finki.bank.domain.User;
import com.finki.bank.domain.enumerations.Currency;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDto implements Serializable {

    private Long id;

    private BigDecimal balance;

    @NotNull
    private String currency;

    private String status;

    private String ownerFirstName;

    private Long ownerId;
}
