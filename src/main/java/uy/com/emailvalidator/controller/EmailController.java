package uy.com.emailvalidator.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uy.com.emailvalidator.domain.EmailValidation;
import uy.com.emailvalidator.service.ValidationService;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    ValidationService validator;

    private static final Logger log = Logger.getLogger(EmailController.class);


    @RequestMapping(value = "emails/check", method = RequestMethod.GET)
    public EmailValidation getEmailValidation(@RequestParam(required = false) String email) {

        log.debug("Incoming request for emails/check with email value -> " + email);

        return validator.getEmailValidation(email);
    }

    @RequestMapping(value = "emails", method = RequestMethod.GET)
    public List<EmailValidation> getEmailValidations(@RequestParam(required = false, defaultValue = "0") Integer page) {

        log.debug("Incoming request for emails with page value -> " + page);

        return validator.getEmailValidations(page);
    }


}
