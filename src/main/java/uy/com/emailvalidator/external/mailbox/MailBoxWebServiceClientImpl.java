package uy.com.emailvalidator.external.mailbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uy.com.emailvalidator.domain.EmailValidation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class MailBoxWebServiceClientImpl implements MailBoxWebServiceClient {

    @Value("${apilayer.key}")
    private String apiKey;

    @Value("${apilayer.host}")
    private String apiHost;

    @Value("${apilayer.path}")
    private String apiPath;

    @Value("${apilayer.scheme}")
    private String apiScheme;

    @Value("${apilayer.timeout}")
    private Integer timeout;

    private final static String ACCESS_KEY_PARAM = "access_key";
    private final static String EMAIL_PARAM = "email";


    private static final Logger log = Logger.getLogger(MailBoxWebServiceClientImpl.class);

    private RequestConfig getTimeoutConfig() {

        return RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

    }

    @Override
    public EmailValidation validateEmail(String address) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(getTimeoutConfig()).build()) {

            URI uri = new URIBuilder()
                    .setScheme(apiScheme)
                    .setHost(apiHost)
                    .setPath(apiPath)
                    .setParameter(ACCESS_KEY_PARAM, apiKey)
                    .setParameter(EMAIL_PARAM, address)
                    .build();
            HttpGet httpget = new HttpGet(uri);
            try (CloseableHttpResponse response = httpClient.execute(httpget)) {

                if (response != null) {

                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        return getValidationResult(response);
                    } else {
                        log.warn("Response status code from invocation -> " + response.getStatusLine().getStatusCode());
                        throw new MailBoxBackendException("An error ocurred while communicating with external services");
                    }
                } else {
                    log.error("Null response from apilayer backend");
                    throw new MailBoxBackendException("An error ocurred while communicating with external services");
                }
            }
        } catch (IOException | URISyntaxException e) {
            log.error("Error while invoking apilayer backend", e);
            throw new MailBoxBackendException("An error ocurred while communicating with external services");
        }


    }

    public EmailValidation getValidationResult(CloseableHttpResponse response) {

        String jsonString = null;
        try {
            jsonString = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8.name());
            ObjectMapper mapper = new ObjectMapper();
            EmailValidation result = mapper.readValue(jsonString, EmailValidation.class);
            return result;
        } catch (IOException e) {
            processError(jsonString);
        }
        return null;
    }

    private void processError(String jsonResponse) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(jsonResponse, Map.class);
            Boolean success = (Boolean) map.get("success");
            if (Boolean.FALSE.equals(success)) {
                LinkedHashMap<String, Object> error = (LinkedHashMap) map.get("error");
                Integer code = (Integer) error.get("code");
                String type = (String) error.get("type");
                String info = (String) error.get("info");
                String errorMessage = "Error while invoking backend, code ->" + code + ", type -> " + type + ", info -> " + info;
                log.error(errorMessage);
                throw new MailBoxBackendException(errorMessage);
            }
        } catch (IOException e) {
            String error = "Error while parsing error from apilayer backend";
            log.error(error, e);
            throw new MailBoxBackendException(error);
        }
    }


}
