package uy.com.emailvalidator.service;

import org.springframework.http.HttpStatus;

public class BadRequestException extends IllegalArgumentException {

    Integer code;
    String message;

    public BadRequestException(String message) {
        super();
        this.code = HttpStatus.BAD_REQUEST.value();
        this.message = message;
    }

    @Override
    public String toString() {
        return this.code + " : " + this.message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
