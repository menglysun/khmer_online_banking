package org.springclass.onlinebankingsystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        System.out.println(">>>>>>>>> CustomAuthenticationEntryPoint");

        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String message = "Unauthorized";

        if (request.getAttribute("logout") != null) {
            message = "This user has been logout, Please login again.";
        }
        else if (response.getStatus() == 401) {
            message = "Unauthorized";
        }
        else if (response.getStatus() == 404) {
            message = "Not Found";
            status = HttpServletResponse.SC_NOT_FOUND;
        }

        // Build JSON response
        Map<String, Object> body = new HashMap<>();

        body.put("error", HttpStatus.valueOf(status).getReasonPhrase());
        body.put("status", status);
        body.put("message", message);

        // Set status + content
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
