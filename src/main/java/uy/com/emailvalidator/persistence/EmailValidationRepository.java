package uy.com.emailvalidator.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import uy.com.emailvalidator.domain.EmailValidation;


public interface EmailValidationRepository extends PagingAndSortingRepository<EmailValidation, Long> {

    EmailValidation findByEmail(String emailAddress);

    Page<EmailValidation> findAllByOrderByEmail(Pageable pageable);

    EmailValidation save(EmailValidation emailValidation);
}
