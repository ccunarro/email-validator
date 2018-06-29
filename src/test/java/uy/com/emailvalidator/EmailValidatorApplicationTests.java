package uy.com.emailvalidator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uy.com.emailvalidator.domain.EmailValidation;
import uy.com.emailvalidator.external.mailbox.MailBoxBackendException;
import uy.com.emailvalidator.external.mailbox.MailBoxWebServiceClient;
import uy.com.emailvalidator.service.ValidationService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailValidatorApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailBoxWebServiceClient mailboxService;

    @Autowired
    private ValidationService validationService;

    @Test
    public void checkEmailWithNoParameter() throws Exception {

        String jsonError = "{\n" +
                "  \"code\": 400,\n" +
                "  \"message\": \"Email cannot be empty\"\n" +
                "}";

        mockMvc.perform(get("/emails/check?email=")).andDo(print()).andExpect(status().is4xxClientError()).andExpect(content().json(jsonError));
    }

    @Test
    public void checkEmailSuccess() throws Exception {

        EmailValidation mockResponse = new EmailValidation();
        mockResponse.setEmail("success@gmail.com");
        mockResponse.setScore(0.64f);
        mockResponse.setSmtpCheck(false);
        mockResponse.setUser("success");
        mockResponse.setRole(false);
        mockResponse.setMxFound(false);
        mockResponse.setCatchAll(null);
        mockResponse.setDomain("gmail.com");
        mockResponse.setDisposable(false);
        mockResponse.setFree(true);
        mockResponse.setDidYouMean("");
        mockResponse.setFormatValid(true);


        when(mailboxService.validateEmail("success@gmail.com")).thenReturn(mockResponse);

        String jsonExpected = "{\n" +
                "  \"email\": \"success@gmail.com\",\n" +
                "  \"user\": \"success\",\n" +
                "  \"domain\": \"gmail.com\",\n" +
                "  \"role\": false,\n" +
                "  \"disposable\": false,\n" +
                "  \"free\": true,\n" +
                "  \"score\": 0.64,\n" +
                "  \"did_you_mean\": \"\",\n" +
                "  \"format_valid\": true,\n" +
                "  \"mx_found\": false,\n" +
                "  \"smtp_check\": false,\n" +
                "  \"catch_all\": null\n" +
                "}";
        mockMvc.perform(get("/emails/check?email=success@gmail.com")).andDo(print()).andExpect(status().isOk()
        ).andExpect(content().json(jsonExpected));
    }

    @Test
    public void checkEmailValidationsList() throws Exception {

        Assert.assertEquals(new Long(0), validationService.getEmailValidations(0).getTotalItems());

        EmailValidation firstMock = new EmailValidation();
        firstMock.setEmail("firstemail@gmail.com");
        when(mailboxService.validateEmail("firstemail@gmail.com")).thenReturn(firstMock);

        EmailValidation secondMock = new EmailValidation();
        secondMock.setEmail("secondemail@gmail.com");
        when(mailboxService.validateEmail("secondemail@gmail.com")).thenReturn(secondMock);

        EmailValidation thirdMock = new EmailValidation();
        thirdMock.setEmail("thirdemail@gmail.com");
        when(mailboxService.validateEmail("thirdemail@gmail.com")).thenReturn(thirdMock);

        validationService.getEmailValidation("firstemail@gmail.com");
        validationService.getEmailValidation("secondemail@gmail.com");
        validationService.getEmailValidation("thirdemail@gmail.com");

        Assert.assertEquals(new Long(3), validationService.getEmailValidations(0).getTotalItems());
    }

    @Test
    public void checkMailBoxApiBackendErrors() throws Exception {
        MailBoxBackendException ex = new MailBoxBackendException("An error ocurred while communicating with external services");
        when(mailboxService.validateEmail("error500@gmail.com")).thenThrow(ex);

        String jsonError = "{\n" +
                "  \"code\": 500,\n" +
                "  \"message\": \"An error ocurred while communicating with external services\"\n" +
                "}";

        mockMvc.perform(get("/emails/check?email=error500@gmail.com")).andDo(print()).andExpect(status().is5xxServerError()).andExpect(content().json(jsonError));
    }




}
