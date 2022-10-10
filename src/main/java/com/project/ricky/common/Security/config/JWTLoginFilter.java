package com.project.ricky.common.Security.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ricky.common.utils.Constants;
import com.project.ricky.user.dto.User;
import com.project.ricky.user.dto.UserDetail;
import com.project.ricky.user.service.UserSecurityService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 이용하여 Json 타입을 객체에 담는다.
    private final AuthenticationManager authenticationManager;
    private final UserSecurityService userSecurityService;

    public JWTLoginFilter(
            AuthenticationManager authenticationManager,    // AuthenticationManager 주입을 받는다.
            UserSecurityService userSecurityService
    ) {
        this.authenticationManager = authenticationManager;
        this.userSecurityService = userSecurityService;
        setFilterProcessesUrl("/auth/login");
    }

    @SneakyThrows  // try, catch 역할 어너테이션
    @Override      // 통행증을 발급 받기 위한 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("############################로그인시도####################################");
        UserLogin userLogin = objectMapper.readValue(request.getInputStream(), UserLogin.class);
        String refreshToken = request.getHeader(Constants.REFRESH_TOKEN);
        if (refreshToken == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userLogin.getUserEmail(), userLogin.getUserPassword(), null
            );
            // 1. UserDetailsService 의 findByUserEmail() 함수 실행됨.
            // 2. UserDetailsService 의 사용자가 있으면 권한부여 받고 -> successfulAuthentication()로 호출한다.
            return authenticationManager.authenticate(authToken);
        } else {

            VerifyResult verify = JWTUtil.verify(refreshToken);

            // 토큰기간이 유효한 경우...
            if (verify.isSuccess()) {

                // DB에 담긴 토큰을 가져온다.
                User user = userSecurityService.findByRefreshToken(userLogin.getUserId());

                // DB에 담긴토큰과 받아온 토큰이 일치하면 auth 토큰을 재갱신 해준다.
                if (!user.getRefreshToken().isEmpty() && user.getRefreshToken().equals(refreshToken)) {

                    UserDetail userDetail = (UserDetail) userSecurityService.loadUserByUsername(verify.getUsername());
                    return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getAuthorities());

                } else {
                    throw new TokenExpiredException("402"); // 토큰발급 오류
                }
            } else {
                // 리플래쉬 토큰이 유효 하지 않다면 Exception ... 로그아웃
                throw new TokenExpiredException("403");
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

        String refreshToken = JWTUtil.makeRefreshToken(userDetail);

        userSecurityService.updateRefreshToken(refreshToken, userDetail.getUser().getUserId()); // 로그인 성공 후 토큰 DB저장.

        response.setHeader(Constants.REFRESH_TOKEN, refreshToken);
        response.setHeader(Constants.AUTH_TOKEN, JWTUtil.makeAuthToken(userDetail));

        response.getOutputStream().write(objectMapper.writeValueAsBytes(userDetail.getUser()));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    // 로그인 실패시 처리
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        System.out.println("===================================================로그인실패");
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
