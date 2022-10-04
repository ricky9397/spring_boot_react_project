package com.project.ricky.common.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/JWT")
public class JWTController {

    @PostMapping("/getToken")
    public void getTokenIssuance(HttpServletRequest request, HttpServletResponse response) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(bearer);
    }


}
