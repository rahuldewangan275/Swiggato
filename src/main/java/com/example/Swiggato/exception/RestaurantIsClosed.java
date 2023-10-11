package com.example.Swiggato.exception;

public class RestaurantIsClosed extends  RuntimeException{
    public  RestaurantIsClosed(String msg){
        super(msg);
    }
}
