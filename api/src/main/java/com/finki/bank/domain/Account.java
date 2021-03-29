package com.finki.bank.domain;

import com.finki.bank.domain.enumerations.AccountStatusType;
import com.finki.bank.domain.enumerations.Currency;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
public class Account extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatusType status;

    @ManyToOne
    private User owner;

//    @Transient
//    public void addFunds(BigDecimal amount) {
//        balance = balance.add(amount);
//    }
}
