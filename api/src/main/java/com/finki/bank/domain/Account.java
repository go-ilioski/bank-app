package com.finki.bank.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account extends TimestampEntity {

    @Id
    private Long id;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;
}
