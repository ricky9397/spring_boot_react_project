package com.project.ricky.common.Security.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ricky.user.service.UserSecurityService;
import com.project.ricky.user.vo.UserDetail;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 이용하여 Json 타입을 객체에 담는다.
    private final AuthenticationManager authenticationManager;
    private final UserSecurityService userSecurityService;

    public JWTLoginFilter(
            AuthenticationManager authenticationManager,    // AuthenticationManager 주입을 받는다.
            RememberMeServices rememberMeServices,       // UsernamePasswordAuthenticationFilter 가 rememberMeServices 필요로 하기때문에 주입을 받는다.
            UserSecurityService userSecurityService
    ) {
        this.authenticationManager = authenticationManager;
        this.userSecurityService = userSecurityService;
        this.setRememberMeServices(rememberMeServices);
        setFilterProcessesUrl("/auth/login");
    }


    @SneakyThrows  // try, catch 역할 어너테이션
    @Override      // 통행증을 발급 받기 위한 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("=====================로그인 시도===================");
        UserLogin userLogin = objectMapper.readValue(request.getInputStream(), UserLogin.class);
        if (userLogin.getRefreshToken() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userLogin.getUserEmail(), userLogin.getUserPassword(), null
            );
            // 1. UserDetailsService 의 findByUserEmail() 함수 실행됨.
            // 2. UserDetailsService 의 사용자가 있으면 권한부여 받고 -> successfulAuthentication()로 호출한다.
            return authenticationManager.authenticate(authToken);
        } else {
            VerifyResult verify = JWTUtil.verify(userLogin.getRefreshToken());
            if (verify.isSuccess()) { // 유효한토큰일 경우..
                UserDetail userDetail = (UserDetail) userSecurityService.loadUserByUsername(verify.getUsername());
                return new UsernamePasswordAuthenticationToken(
                        userDetail, userDetail.getAuthorities()
                );
            } else { // 유효하지않다면 Exception
                throw new TokenExpiredException("refresh token expired");
            }
        }
//                .rememberme(request.getParameter("remember-me") != null)

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws ServletException, IOException {

        UserDetail userDetail = (UserDetail) authResult.getPrincipal(); // 성공한 유저정보를 UserDetail객체에 담는다.

        logger.info("=======================토큰 발행 시작=======================================");
        response.setHeader("auth_token", JWTUtil.makeAuthToken(userDetail));
        response.setHeader("refresh_token", JWTUtil.makeRefreshToken(userDetail));
        logger.info("=======================토큰 발행 끝========================================");
        response.getOutputStream().write(objectMapper.writeValueAsBytes(userDetail));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
    
    
    // 로그인 실패시 처리
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        System.out.println("===================================================2");
        super.unsuccessfulAuthentication(request, response, failed);
    }


}
