package uy.com.emailvalidator.service;

import uy.com.emailvalidator.domain.EmailValidation;

import java.util.List;

public class EmailValidationsResponse {

    private List<EmailValidation> validations;
    private Integer actualPage;
    private Integer totalPages;
    private Long totalItems;

    public EmailValidationsResponse(List<EmailValidation> validations, Integer actualPage, Integer totalPages, Long totalItems) {
        this.validations = validations;
        this.actualPage = actualPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<EmailValidation> getValidations() {
        return validations;
    }

    public void setValidations(List<EmailValidation> validations) {
        this.validations = validations;
    }

    public Integer getActualPage() {
        return actualPage;
    }

    public void setActualPage(Integer actualPage) {
        this.actualPage = actualPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
}
