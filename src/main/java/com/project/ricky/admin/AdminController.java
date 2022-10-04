package com.project.ricky.admin;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    @GetMapping("/dashboard")
    public ResponseEntity<?> test() throws TokenExpiredException {
        System.out.println("test당");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
