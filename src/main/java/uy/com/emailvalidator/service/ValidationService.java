package uy.com.emailvalidator.service;

import uy.com.emailvalidator.domain.EmailValidation;

import java.util.List;

public interface ValidationService {

    EmailValidation getEmailValidation(String email);

    List<EmailValidation> getEmailValidations(Integer page);

}
