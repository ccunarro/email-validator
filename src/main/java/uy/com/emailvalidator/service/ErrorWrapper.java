package uy.com.emailvalidator.service;

import org.springframework.http.HttpStatus;
import uy.com.emailvalidator.external.mailbox.MailBoxBackendException;

public class ErrorWrapper {

    Integer code;
    String message;

    public ErrorWrapper(BadRequestException ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

    public ErrorWrapper(MailBoxBackendException ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

    public ErrorWrapper() {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = "Unexpected error";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
