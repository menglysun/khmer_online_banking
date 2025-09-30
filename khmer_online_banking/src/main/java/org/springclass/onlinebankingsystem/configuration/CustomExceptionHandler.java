package org.springclass.onlinebankingsystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springclass.onlinebankingsystem.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpStatusCodeException.class})
    protected ResponseEntity<Object> handleHttpException(final HttpStatusCodeException e, final WebRequest request) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final Map<String, String> content = new LinkedHashMap<>();
        content.put("timestamp", String.valueOf(System.currentTimeMillis()));
        content.put("status", String.valueOf(e.getStatusCode().value()));
        content.put("error", e.getStatusCode().toString());
        content.put("message", e.getStatusText());
        content.put("path", request.getContextPath());

        try {
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(content), headers, e.getStatusCode());
        } catch (Exception ex) {
            log.error("Error serializing the response body", ex);
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<Object> handleException(final UnauthorizedException e) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final Map<String, String> content = new LinkedHashMap<>();
        content.put("timestamp", String.valueOf(System.currentTimeMillis()));
        content.put("status", String.valueOf(e.getError()));
        content.put("error", "UNAUTHORIZED");
        content.put("message", e.getMessage());

        try {
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(content), headers, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Error serializing the response body", ex);
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<Object> handleException(final CustomException e) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final Map<String, String> content = new LinkedHashMap<>();
        content.put("timestamp", String.valueOf(System.currentTimeMillis()));
        content.put("status", String.valueOf(e.getStatus()));
        content.put("error", String.valueOf(e.getStatus()));
        content.put("message", e.getMessage());

        try {
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(content), headers, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Error serializing the response body", ex);
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
