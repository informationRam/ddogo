package com.yumpro.ddogo.mymap.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//조회된 결과가 없을 경우 발생하는 예외처리클래스
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String msg){
        super(msg);
    }
}
