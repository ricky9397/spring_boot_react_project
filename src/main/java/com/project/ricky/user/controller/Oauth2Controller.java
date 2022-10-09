package com.project.ricky.user.controller;

import com.project.ricky.common.Security.config.oauth2.OAuth2LoginUserService;
import com.project.ricky.user.dto.Oauth2Test;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/login")
public class Oauth2Controller {

    private final OAuth2LoginUserService oAuth2LoginUserService;

    @PostMapping("/google")
    public ResponseEntity<?> LoginWithGoogleOauth2(@RequestBody Map<String, Object> data) {
        System.out.println(data);
        return ResponseEntity.ok().build();
    }

}
