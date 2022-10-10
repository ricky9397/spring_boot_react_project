package com.project.ricky.user.controller;

import com.project.ricky.common.Security.config.JWTUtil;
import com.project.ricky.common.utils.Constants;
import com.project.ricky.user.dto.User;
import com.project.ricky.user.service.UserSecurityService;
import com.project.ricky.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class Oauth2Controller {

    private final UserService userService;
    private final UserSecurityService userSecurityService;

    /**
     * OAuth2 로그인 / 회원가입 {urlid} = (구글, 네이버, 카카오)
     * @param data
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/login/{urlid}")
    public ResponseEntity<?> LoginWithGoogleOauth2(@PathVariable("urlid") String urlid, @RequestBody Map<String, Object> data, HttpServletResponse response) throws Exception {
        User user = null;
        Optional<User> userInfo = userService.findByUserEmail((String) data.get("email"));

        if (userInfo.isEmpty()) {
            // 정보가 없을 경우 자동 구글 회원가입
            user = userService.save(data);
        } else {
            user = userInfo.get();
        }
        String refreshToken = JWTUtil.makeRefreshToken(user);
        userSecurityService.updateRefreshToken(refreshToken, user.getUserId());

        response.setHeader(Constants.REFRESH_TOKEN, refreshToken);
        response.setHeader(Constants.AUTH_TOKEN, JWTUtil.makeAuthToken(user));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
