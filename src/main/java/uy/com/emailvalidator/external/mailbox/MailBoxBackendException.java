package uy.com.emailvalidator.external.mailbox;

import org.springframework.http.HttpStatus;

public class MailBoxBackendException extends RuntimeException {

    Integer code;
    String message;

    public MailBoxBackendException(String message) {
        super();
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
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
