package com.project.ricky.user.controller;

import com.project.ricky.common.Utils;
import com.project.ricky.user.service.UserService;
import com.project.ricky.user.vo.User;
import com.project.ricky.user.vo.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception {
        try {
            user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            user.setUserPhone(bCryptPasswordEncoder.encode(user.getUserPhone()));
            Long result = userService.register(user);
            if (result == 0) {
                return new ResponseEntity<>(result, HttpStatus.EXPECTATION_FAILED);
            } else {

                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            // 409 중복 에러코드 ( 에러 핸들러 작성시 수정 )
            return new ResponseEntity<>(409, HttpStatus.CONFLICT);
        }

//        return new ResponseEntity<>(2, HttpStatus.OK);
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

    @PostMapping("/check")
    public ResponseEntity<?> getSuccessCheck(@RequestBody User user) throws Exception {
        if (!Utils.isEmpty(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws Exception {
        return new ResponseEntity<>("로그아웃", HttpStatus.OK); 
    }

}
