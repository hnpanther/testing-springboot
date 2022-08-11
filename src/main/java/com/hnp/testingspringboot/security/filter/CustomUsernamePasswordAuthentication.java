package com.hnp.testingspringboot.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnp.testingspringboot.model.AuthRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthentication extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    public CustomUsernamePasswordAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("=================================");
        System.out.println("in custom auth filter");


        try {
            AuthRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthRequest.class);

            System.out.println("user pass : " + authenticationRequest.getUsername() + " --- " + authenticationRequest.getPassword());
            System.out.println("=================================");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("=================================");
        System.out.println("susccess auth!");
        System.out.println("=================================");
    }
}
