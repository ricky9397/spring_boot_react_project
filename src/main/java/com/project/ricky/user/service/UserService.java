package com.project.ricky.user.service;

import com.project.ricky.common.utils.Constants;
import com.project.ricky.user.repository.UserRepository;
import com.project.ricky.user.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public Long register(User user) throws Exception{
        user.setLockedYn(Constants.NO);
        user.setUseYn(Constants.YES);
        user.setRole(Constants.ROLE_USER);
        user.setModDate(Constants.MODDATE);
        user.setLoginDate(Constants.LOGINDATE);
        user.setRegDate(Constants.REGDATE);
        return userRepository.save(user).getUserId();
    }

    public Optional<User> login(User user) throws Exception {
        log.info("login ===================== {}" , user);
        Optional<User> result = userRepository.findByUserEmail(user.getUserEmail());
        if(result != null){
            System.out.println("UserService 로그인 성공");
        } else {
            System.out.println("UserService 로그인 실패");
        }
        return result;
    }
}
