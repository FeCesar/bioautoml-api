package com.bioautoml.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProcessNotValid extends RuntimeException{
    public ProcessNotValid(String message){
        super(message);
    }
}
