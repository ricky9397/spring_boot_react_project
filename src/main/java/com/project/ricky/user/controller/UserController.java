package com.project.ricky.user.controller;

import com.project.ricky.common.Security.config.UserLogin;
import com.project.ricky.user.service.UserService;
import com.project.ricky.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception{
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        Long result = userService.register(user);
        if(result == 0){
            return new ResponseEntity<>(result, HttpStatus.EXPECTATION_FAILED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

//    @PostMapping("/logins")
//    public ResponseEntity<?> login(@RequestBody User user) throws Exception {
//        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
//        System.out.println("user =============== " + user);
//
////        Optional<User> result = userService.login(user);
//        Optional<User> result = userService.login(user);
//        if(result != null){
//            System.out.println("성공");
//        } else {
//            System.out.println("실패");
//        }
//        System.out.println("예????????");
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

}
