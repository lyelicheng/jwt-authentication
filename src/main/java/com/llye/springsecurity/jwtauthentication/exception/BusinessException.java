package com.llye.springsecurity.jwtauthentication.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Value
public class BusinessException extends Exception {
    HttpStatus httpStatus;
    String errorMessage;

    public BusinessException(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
