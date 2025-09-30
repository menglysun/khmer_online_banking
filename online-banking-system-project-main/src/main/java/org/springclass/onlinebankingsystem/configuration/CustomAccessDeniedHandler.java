package org.springclass.onlinebankingsystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springclass.onlinebankingsystem.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        System.out.println(">>>>>>>>> customAccessDeniedHandler");

        var exception = new CustomException(401, "UNAUTHORIZED");

        if (request.getAttribute("invalid") != null) {
            exception.setMessage("Unauthorized");
            exception.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        exception.setStatus(HttpServletResponse.SC_FORBIDDEN);
        exception.setMessage(accessDeniedException.getLocalizedMessage());

        Map<String, Object> body = new HashMap<>();

        body.put("error", HttpStatus.UNAUTHORIZED);
        body.put("status", exception.getStatus());
        body.put("message", exception.getMessage());

        response.setStatus(exception.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
