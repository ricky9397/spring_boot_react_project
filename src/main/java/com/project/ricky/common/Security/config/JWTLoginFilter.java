package com.project.ricky.common.Security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ricky.user.vo.UserDetail;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {


    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 이용하여 Json 타입을 객체에 담는다.
    private final AuthenticationManager authenticationManager;

    public JWTLoginFilter(
            AuthenticationManager authenticationManager, // AuthenticationManager 주입을 받는다.
            RememberMeServices rememberMeServices       // UsernamePasswordAuthenticationFilter 가 rememberMeServices 필요로 하기때문에 주입을 받는다.
    ) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/logins", "POST"));
        this.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        this.setAuthenticationFailureHandler(new LoginFailureHandler());
        this.setRememberMeServices(rememberMeServices);
    }

    
    @SneakyThrows  // try, catch 역할 어너테이션
    @Override      // 통행증을 발급 받기 위한 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("=====================로그인 시도===================");
        UserLogin userLogin = objectMapper.readValue(request.getInputStream(), UserLogin.class);

//        UserLogin userLogin = UserLogin.builder()
//                .username(request.getParameter("username"))
//                .password(request.getParameter("password"))
//                .site(request.getParameter("site"))
//                .rememberme(request.getParameter("remember-me") != null)
//                .build();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLogin.getUserEmail(), userLogin.getUserPassword(), null
        );

        // 1. UserDetailsService 의 findByUserEmail() 함수 실행됨.
        // 2. UserDetailsService 의 사용자가 있으면 권한부여 받고 -> successfulAuthentication()로 호출한다.
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws ServletException, IOException {

        UserDetail userDetail = (UserDetail) authResult.getPrincipal();


        System.out.println("=========================================================================================");
        System.out.println("========================successfulAuthentication========================================");
        System.out.println("=========================================================================================");
        super.successfulAuthentication(request, response, chain, authResult);
    }

//    @Override
//    protected void unsuccessfulAuthentication(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException failed) throws IOException, ServletException {
//        System.out.println("===================================================2");
//        super.unsuccessfulAuthentication(request, response, failed);
//    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
