package com.finki.bank.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RegisterUserDto extends UserDto implements Serializable {

    @NotNull
    private String password;
}
