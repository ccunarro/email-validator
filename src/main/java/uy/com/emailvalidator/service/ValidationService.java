package uy.com.emailvalidator.service;

import uy.com.emailvalidator.domain.EmailValidation;

public interface ValidationService {

    EmailValidation getEmailValidation(String email);

    EmailValidationsResponse getEmailValidations(Integer page);

}
