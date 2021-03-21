package com.finki.bank.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */

@Data
public class LoginRequestDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;  //username = email

    @NotNull
    @Size(min = 4, max = 100)
    private String password;



}
