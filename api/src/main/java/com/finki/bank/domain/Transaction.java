package com.finki.bank.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String status;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    private String currency;

    private String description;

    @Column(name = "commission", precision = 21, scale = 2)
    private BigDecimal commission;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;
}
