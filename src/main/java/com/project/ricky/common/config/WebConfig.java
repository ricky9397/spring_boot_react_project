package com.project.ricky.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry CorsRegistry) {
        CorsRegistry
                .addMapping("/**") // 프로그램에서 제공하는 URL
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://192.168.219.100:3000")
                .allowedOrigins("http://192.168.219.100")
                .allowedHeaders("*") // 어떤 헤더들을 허용할 것인지
                .allowedMethods( // 어떤 메서드를 허용할 것인지 (GET, POST...)
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.HEAD.name()
                );
    }


}
