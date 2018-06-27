package uy.com.emailvalidator.external.mailbox;

import uy.com.emailvalidator.domain.EmailValidation;

public interface MailBoxWebServiceClient {

    EmailValidation validateEmail(String address);
}
