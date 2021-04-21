package com.finki.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finki.bank.domain.enumerations.Currency;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "requests")
public class Request extends TimestampEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "description")
    private String description;

    private String type;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;
}
