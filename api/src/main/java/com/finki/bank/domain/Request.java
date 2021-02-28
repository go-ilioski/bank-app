package com.finki.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "description")
    private String description;
}
