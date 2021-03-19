package com.finki.bank.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestAlertException extends RuntimeException {
    public BadRequestAlertException(){
        super("A new user cannot already have an ID");
    }

    public BadRequestAlertException(String message){
        super(message);
    }
}
