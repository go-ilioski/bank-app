package com.finki.bank.domain.enumerations;

import lombok.Data;

import java.math.BigDecimal;
public enum TransactionType {

    WITHDRAW(new BigDecimal("5.00")),
    DEPOSIT(new BigDecimal("0.00")),
    PAYMENT(new BigDecimal("10.00"));

    private final BigDecimal commissionRate;

    TransactionType(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }
}
