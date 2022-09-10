package com.project.ricky.common.Security.config;

import com.project.ricky.common.config.CorsConfig;
import com.project.ricky.user.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserSecurityService userSecurityService;

    private final CorsConfig corsConfig;

    @Bean // bean 은 해당 메서드의 리턴되는 오브젝트를 Ioc 로 등록해준다.
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(encoderPwd());
    }

    // 시큐리티에서 제공하는 cookie 에 Remember Me 기능
    private RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(
                "paper-site-remember-me",
                userSecurityService
        );
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setTokenValiditySeconds(3600);

        return rememberMeServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), rememberMeServices(), userSecurityService);
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), userSecurityService);
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()   // csrf 보안 설정을 비활성화한다.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 시큐리티 세션을 사용하지 않음.
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class) // 로그인처리필터
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class) // 토큰검증필터
                .authorizeRequests(config -> {
                    config
                            .antMatchers("/").permitAll()
                            .antMatchers("/auth/login").permitAll()
//                            .antMatchers("/error").permitAll()
                            .antMatchers("/auth/signup/*").permitAll()
                            .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                            .antMatchers("/auth/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                            .antMatchers("/auth/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEACHER")
                    ;
                });
//                .rememberMe(config -> {
//                    config.rememberMeServices(rememberMeServices())
//                    ;
//                })
//                .exceptionHandling(exception -> {
//                    exception.accessDeniedPage("/access-denied");
//                })
                }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    }
