package com.studyretro.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String msg){
        super(msg);
    }
}
