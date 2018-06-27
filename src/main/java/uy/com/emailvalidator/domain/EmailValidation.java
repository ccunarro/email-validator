package uy.com.emailvalidator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@JsonIgnoreProperties(value = {"id"})
@Entity
public class EmailValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String email;
    String didYouMean;
    String user;
    String domain;
    Boolean formatValid;
    Boolean mxFound;
    Boolean smtpCheck;
    Boolean catchAll;
    Boolean role;
    Boolean disposable;
    Boolean free;
    Float score;

    public EmailValidation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDidYouMean() {
        return didYouMean;
    }

    @JsonSetter("did_you_mean")
    public void setDidYouMean(String didYouMean) {
        this.didYouMean = didYouMean;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getFormatValid() {
        return formatValid;
    }

    @JsonSetter("format_valid")
    public void setFormatValid(Boolean formatValid) {
        this.formatValid = formatValid;
    }

    public Boolean getMxFound() {
        return mxFound;
    }

    @JsonSetter("mx_found")
    public void setMxFound(Boolean mxFound) {
        this.mxFound = mxFound;
    }

    public Boolean getSmtpCheck() {
        return smtpCheck;
    }

    @JsonSetter("smtp_check")
    public void setSmtpCheck(Boolean smtpCheck) {
        this.smtpCheck = smtpCheck;
    }

    public Boolean getCatchAll() {
        return catchAll;
    }

    @JsonSetter("catch_all")
    public void setCatchAll(Boolean catchAll) {
        this.catchAll = catchAll;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public Boolean getDisposable() {
        return disposable;
    }

    public void setDisposable(Boolean disposable) {
        this.disposable = disposable;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
