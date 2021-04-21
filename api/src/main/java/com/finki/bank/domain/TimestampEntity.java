package com.finki.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public abstract class TimestampEntity {

//    @CreatedBy
//    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
//    @JsonIgnore
//    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

//    @LastModifiedBy
//    @Column(name = "update_by", length = 50)
//    @JsonIgnore
//    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    @JsonIgnore
    private LocalDateTime updatedDate = LocalDateTime.now();
}