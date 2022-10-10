package com.project.ricky.common.Security.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ricky.common.utils.Constants;
import com.project.ricky.user.service.UserSecurityService;
import com.project.ricky.user.dto.UserDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1.인증요청이 있을때 요청하는 것이 아님..
// 2.시큐리티가 filter 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있다.
// 3.권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무저건 타게 되어있음.
// 4.만약에 권한이 인증이 필요한 주소가 아니라면 이필터를 안탄다.
public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final UserSecurityService userSecurityService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper를 이용하여 Json 타입을 객체에 담는다.

    public JWTCheckFilter(AuthenticationManager authenticationManager, UserSecurityService userSecurityService) {
        super(authenticationManager);
        this.userSecurityService = userSecurityService;
    }

    @Override // 인증이나 권한이 필여한 주소요청이 있을 때 해당 필터를 타게 됨.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authToken = request.getHeader(Constants.AUTH_TOKEN);
        logger.info("#####################################Token 체크 시작##########################################");

        // header가 있는지 확인
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        String token = authToken.substring("Bearer ".length());
        VerifyResult result = JWTUtil.verify(token);
        if (result.isSuccess()) { // header로 받은 authToken을 verify 체크 후 만료되었을경우 false
            UserDetail userDetail = (UserDetail) userSecurityService.loadUserByUsername(result.getUsername());

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    userDetail.getUsername(), null, userDetail.getAuthorities()
            );
            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
            SecurityContextHolder.getContext().setAuthentication(userToken);
            logger.info("#####################Token 발급완료.####################################");
            chain.doFilter(request, response);
        } else {
            throw new TokenExpiredException("401");
        }
    }
}
