package uy.com.emailvalidator.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uy.com.emailvalidator.external.mailbox.MailBoxBackendException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object processValidationError(BadRequestException ex) {
        return new ErrorWrapper(ex);
    }

    @ExceptionHandler(MailBoxBackendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object processWebServiceError(MailBoxBackendException ex) {

        return new ErrorWrapper(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object processUnexpectedError(RuntimeException ex) {
        return new ErrorWrapper();
    }
}
