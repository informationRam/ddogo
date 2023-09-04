package com.yumpro.ddogo.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND,reason = "entity not found")
public class DataNotFoundException extends RuntimeException{
    public String DataNotFoundException(){
        return "common/ddoError";
    }
}
