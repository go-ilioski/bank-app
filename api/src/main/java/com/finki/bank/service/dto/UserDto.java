package com.finki.bank.service.dto;

import com.finki.bank.domain.Account;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated;

    @NotNull
    private String role;

    //private Set<AccountDto> accounts = new HashSet<>();


}
