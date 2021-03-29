package com.finki.bank.domain;

import com.finki.bank.domain.enumerations.Currency;
import com.finki.bank.domain.enumerations.TransactionStatus;
import com.finki.bank.domain.enumerations.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transactions")
public class Transaction extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    private Currency currency;

    private String description;

    @Column(name = "commission", precision = 21, scale = 2)
    private BigDecimal commission;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;
}
