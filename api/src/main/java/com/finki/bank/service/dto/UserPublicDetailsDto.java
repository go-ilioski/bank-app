package com.finki.bank.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicDetailsDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long accountId;

}
