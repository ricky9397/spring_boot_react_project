package com.project.ricky.common.Security.config.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Oauth2JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 이용하여 Json 타입을 객체에 담는다.

    public Oauth2JWTLoginFilter(
    ) {
        setFilterProcessesUrl("/oauth2/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("구구구구구글~");
        return super.attemptAuthentication(request, response);
    }
}
