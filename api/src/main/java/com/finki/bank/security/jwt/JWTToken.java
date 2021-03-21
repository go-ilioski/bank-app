package com.finki.bank.security.jwt;

import lombok.Data;

@Data
public class JWTToken {

    private String idToken;

    public JWTToken(String idToken) {
        this.idToken = idToken;
    }
}