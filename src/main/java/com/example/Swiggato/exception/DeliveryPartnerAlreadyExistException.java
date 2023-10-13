package com.example.Swiggato.exception;

public class DeliveryPartnerAlreadyExistException extends RuntimeException{

    public DeliveryPartnerAlreadyExistException(String msg){
        super(msg);
    }
}
