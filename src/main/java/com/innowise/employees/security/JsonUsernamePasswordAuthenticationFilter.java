package com.innowise.employees.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();

            String bodyJson = reader.lines().collect(Collectors.joining());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(bodyJson);
            JsonNode usernameNode = tree.get(USERNAME_PROPERTY);
            JsonNode passwordNode = tree.get(PASSWORD_PROPERTY);
            String username = usernameNode.asText();
            String password = passwordNode.asText();

            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            logger.warn("Unable to read user credentials from request", e);
        }
        return null;
    }
}