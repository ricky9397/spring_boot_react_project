package com.project.ricky.common.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(final String msg){
        super("badTokenException : " + msg);
    }


}
