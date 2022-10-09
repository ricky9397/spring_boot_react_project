package com.project.ricky.user.controller;

import com.project.ricky.common.utils.Utils;
import com.project.ricky.user.service.UserService;
import com.project.ricky.user.dto.User;
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

    /**+
     * 관리자, 유저 회원가입
     * 
     * @param user
     * @return
     * @throws Exception
     */
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
    }

    /**+
     * 페이지이동시 첫화면 진입 -> 시큐리티 권한체크 및 로그인상태 체크
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/check")
    public ResponseEntity<?> getSuccessCheck(@RequestBody User user) throws Exception {
        if (!Utils.isEmpty(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
