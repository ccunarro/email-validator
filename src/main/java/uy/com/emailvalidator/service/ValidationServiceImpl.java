package uy.com.emailvalidator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uy.com.emailvalidator.domain.EmailValidation;
import uy.com.emailvalidator.external.mailbox.MailBoxWebServiceClient;
import uy.com.emailvalidator.persistence.EmailValidationRepository;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    EmailValidationRepository emailValidationRepository;

    @Autowired
    MailBoxWebServiceClient webServiceClient;

    @Value("${validator.paginationSize}")
    private Integer paginationSize;

    @Override
    public EmailValidation getEmailValidation(String email) {

        if (StringUtils.isEmpty(email)) {
            throw new BadRequestException("Email cannot be empty");
        }

        EmailValidation emailValidation = emailValidationRepository.findByEmail(email);

        if (emailValidation == null) {
            emailValidation = webServiceClient.validateEmail(email);
            emailValidationRepository.save(emailValidation);
        }

        return emailValidation;
    }

    @Override
    public EmailValidationsResponse getEmailValidations(Integer page) {

        Page<EmailValidation> validations = emailValidationRepository.findAllByOrderByEmail(PageRequest.of(page, paginationSize));
        Integer totalPages = validations.getTotalPages();
        Long totalItems = validations.getTotalElements();
        return new EmailValidationsResponse(validations.getContent(), page, totalPages, totalItems);

    }
}
