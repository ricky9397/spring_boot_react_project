package com.project.ricky.common.Security.config;

import com.project.ricky.common.config.CorsConfig;
import com.project.ricky.common.jwt.JwtAuthenticationFilter;
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
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

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
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

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
        System.out.println("ccccccccccccccccccccccccccccccccccccccccccccc");
        final SpLoginFilter filter = new SpLoginFilter(
                authenticationManagerBean(),
                rememberMeServices()
        );
        System.out.println("ddddddddddddddddddddddddddddddddddddddddddd");
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()   // csrf 보안 설정을 비활성화한다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//              SessionCreationPolicy.ALWAYS - 스프링시큐리티가 항상 세션을 생성
//              SessionCreationPolicy.IF_REQUIRED - 스프링시큐리티가 필요시 생성(기본)
//              SessionCreationPolicy.NEVER - 스프링시큐리티가 생성하지않지만, 기존에 존재하면 사용
//              SessionCreationPolicy.STATELESS - 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음
                .and()
                .formLogin().disable() // 기본 시큐리티 form로그인화면이 나오지 않음.
                .httpBasic().disable() // httpBasic auth 기반으로 로그인 인증창이 뜸. disable 시에 인증창 뜨지 않음.
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login/**").hasRole("ADMIN")
                .anyRequest().permitAll();


//                .formLogin(login -> {
//                    login.loginPage("/auth/login")  // 로그인페이지
//                    ;
//                })
//                .logout(logout -> {
//                    logout.logoutSuccessUrl("/")    // 로그아웃 후 메인페이지이동
//                    ;
//                })
//                .rememberMe(config -> {
//                    config.rememberMeServices(rememberMeServices())
//                    ;
//                })
//                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exception -> {
//                    exception.accessDeniedPage("/access-denied");
//                })
//                .authorizeRequests(config -> {
//                    config
//                            .antMatchers("/").permitAll()
//                            .antMatchers("/auth/login").permitAll()
////                            .antMatchers("/error").permitAll()
//                            .antMatchers("/auth/signup/*").permitAll()
//                            .antMatchers("/study/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                            .antMatchers("/teacher/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEACHER")
//                            .antMatchers("/manager/**").hasAuthority("ROLE_ADMIN")
//                    ;
//                }).cors();
        ;
    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

}
