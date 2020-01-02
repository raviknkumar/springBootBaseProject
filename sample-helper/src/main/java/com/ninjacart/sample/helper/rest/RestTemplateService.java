package com.ninjacart.sample.helper.rest;

import com.ninjacart.sample.helper.exception.SampleException;
import com.ninjacart.sample.helper.exception.errorcode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


@Service
@Slf4j
public class RestTemplateService {
    @Autowired private RestTemplate restTemplate;

    public <T> T makeHttpRequest(Object input, HttpHeaders httpHeaders, UriComponentsBuilder builder, HttpMethod verb, Class<T> clazz) throws SampleException {
        HttpEntity<?> response = null;
        try {
            HttpEntity<?> entity = new HttpEntity<>(input, httpHeaders);
            response = restTemplate.exchange(builder.build().encode().toUri(), verb, entity, clazz);
        } catch (HttpClientErrorException exc) {
            log.error("Excpetion during url:{}",builder.toUriString(),exc);
            handleError(exc);
        } catch (RestClientException exc) {
            log.error("Excpetion during url:{}",builder.toUriString(),exc);
            throw new SampleException(ErrorCode.CLIENT_ERROR);
        }
        return handleSuccess(response);
    }

    public <T> T makeHttpRequest(Object input, HttpHeaders httpHeaders, UriComponentsBuilder builder, HttpMethod verb, ParameterizedTypeReference<T> type) throws SampleException {
        HttpEntity<?> response = null;
        try {
            HttpEntity<?> entity = new HttpEntity<>(input, httpHeaders);
            response = restTemplate.exchange(builder.build().encode().toUri(), verb, entity, type);
        } catch (HttpClientErrorException exc) {
            log.error("Excpetion during url:{}",builder.toUriString(),exc);
            return handleError(exc);
        }
        return handleSuccess(response);
    }

    private <T> T handleError(HttpClientErrorException exc) throws SampleException {
        if (exc.getRootCause() instanceof ConnectException)
            throw new SampleException(ErrorCode.SERVER_UNREACHABLE);
        if (exc.getRootCause() instanceof SocketTimeoutException)
            throw new SampleException(ErrorCode.TIME_OUT);
        throw new SampleException(ErrorCode.CLIENT_ERROR);
    }

    private <T> T handleSuccess(HttpEntity<?> response) throws SampleException {
        if (response == null) throw new SampleException(ErrorCode.EMPTY_RESPONSE);
        return (T) response.getBody();
    }

}

