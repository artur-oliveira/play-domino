package org.playdomino.components.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Getter
public final class AuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * The function sets the response content type to JSON, sets the response status to 401 (Unauthorized), and writes an
     * error message to the response output stream.
     *
     * @param request       The `HttpServletRequest` object represents the HTTP request made by the client. It contains
     *                      information such as the request method, headers, parameters, and body.
     * @param response      The "response" parameter is an object of the HttpServletResponse class, which represents the HTTP
     *                      response that will be sent back to the client. It is used to set the response content type, status, and write the
     *                      response body.
     * @param authException The `authException` parameter is an instance of the `AuthenticationException` class. It
     *                      represents an exception that occurred during the authentication process. It contains information about the specific
     *                      type of exception and the error message associated with it.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        getObjectMapper().writeValue(response.getOutputStream(), org.playdomino.models.generic.GenericError.builder()
                .code(authException.getClass().getSimpleName())
                .message(authException.getMessage())
                .build());
    }
}

