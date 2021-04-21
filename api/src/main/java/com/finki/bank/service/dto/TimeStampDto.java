package com.finki.bank.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TimeStampDto implements Serializable {

    private LocalDateTime createdDate;
}
