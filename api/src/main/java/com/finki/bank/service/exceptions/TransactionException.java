package com.finki.bank.service.exceptions;

public class TransactionException extends RuntimeException{
    public TransactionException(String msg){
        super(msg);
    }
}
