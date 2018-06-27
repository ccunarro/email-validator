package uy.com.emailvalidator.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uy.com.emailvalidator.domain.EmailValidation;
import uy.com.emailvalidator.service.EmailValidationsResponse;
import uy.com.emailvalidator.service.ValidationService;

@RestController
public class EmailController {

    @Autowired
    ValidationService validator;

    private static final Logger log = Logger.getLogger(EmailController.class);


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = "emails/check", method = RequestMethod.GET)
    public EmailValidation getEmailValidation(
            @ApiParam(value = "Email address to validate")
            @RequestParam(required = false) String email) {

        log.debug("Incoming request for emails/check with email value -> " + email);

        return validator.getEmailValidation(email);
    }

    @ApiOperation(value = "List email validations",
            notes = "Paginated service to list email validations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = "emails", method = RequestMethod.GET)
    public EmailValidationsResponse getEmailValidations(
            @ApiParam(value = "Page number (0 for first page)")
            @RequestParam(required = false, defaultValue = "0") Integer page) {

        log.debug("Incoming request for emails with page value -> " + page);

        return validator.getEmailValidations(page);
    }


}
