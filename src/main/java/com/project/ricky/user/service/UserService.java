package com.project.ricky.user.service;

import com.project.ricky.common.utils.Constants;
import com.project.ricky.user.dto.User;
import com.project.ricky.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(User user) throws Exception{
        user.setLockedYn(Constants.NO);
        user.setUseYn(Constants.YES);
        user.setRole(Constants.ROLE_USER);
        user.setModDate(Constants.MODDATE);
        user.setLoginDate(Constants.LOGINDATE);
        user.setRegDate(Constants.REGDATE);
        return userRepository.save(user).getUserId();
    }

    public Optional<User> findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    public User save(Map<String, Object> data) throws Exception {
        User userRequest = User.builder()
                .userEmail((String) data.get("email"))
                .userPassword(bCryptPasswordEncoder.encode("google"))
                .userName((String) data.get("name"))
                .userPhone("00000000000")
                .userYn(Constants.YES)
                .lockedYn(Constants.NO)
                .regDate(Constants.REGDATE)
                .modDate(Constants.MODDATE)
                .loginDate(Constants.LOGINDATE)
                .useYn(Constants.YES)
                .role(Constants.ROLE_USER)
                .providerId((String) data.get("googleId"))
                .provider("google")
                .build();
        return userRepository.save(userRequest);
    }
}
