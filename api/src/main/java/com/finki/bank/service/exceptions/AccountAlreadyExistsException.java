package com.finki.bank.service.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{

    public AccountAlreadyExistsException(){
        super("You already have an acoount!");
    }

}
