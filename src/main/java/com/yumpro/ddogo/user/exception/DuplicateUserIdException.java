package com.yumpro.ddogo.user.exception;
// 500에러시 출력
public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String message) {
        super(message);
    }
}
