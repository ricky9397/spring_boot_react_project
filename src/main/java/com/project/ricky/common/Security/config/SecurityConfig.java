package com.project.ricky.common.Security.config;

import com.project.ricky.user.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserSecurityService userSecurityService;

    @Bean // bean 은 해당 메서드의 리턴되는 오브젝트를 Ioc 로 등록해준다.
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(encoderPwd());
    }

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
        final SpLoginFilter filter = new SpLoginFilter(
                authenticationManagerBean(),
                rememberMeServices()
        );
        http
                .csrf().disable()   // csrf 비활성화
                .formLogin(login -> {
                    login.loginPage("/auth/login")  // 로그인페이지
                    ;
                })
                .logout(logout -> {
                    logout.logoutSuccessUrl("/")    // 로그아웃 후 메인페이지이동
                    ;
                })
                .rememberMe(config -> {
                    config.rememberMeServices(rememberMeServices())
                    ;
                })
                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> {
                    exception.accessDeniedPage("/access-denied");
                })
                .authorizeRequests(config -> {
                    config
                            .antMatchers("/").permitAll()
                            .antMatchers("/auth/login").permitAll()
//                            .antMatchers("/error").permitAll()
                            .antMatchers("/auth/signup/*").permitAll()
                            .antMatchers("/study/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                            .antMatchers("/teacher/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEACHER")
                            .antMatchers("/manager/**").hasAuthority("ROLE_ADMIN")
                    ;
                })
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
