package com.example.Swiggato.exception;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String msg){
        super(msg);
    }

}
