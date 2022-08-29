package com.project.ricky.user.service;

import com.project.ricky.user.repository.UserRepository;
import com.project.ricky.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public Long register(User user) throws Exception{
        return userRepository.save(user).getUserId();
    }

    public int login(User user) throws Exception {
        return 0;
    }
}
