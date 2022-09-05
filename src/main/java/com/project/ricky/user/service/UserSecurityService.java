package com.project.ricky.user.service;

import com.project.ricky.user.repository.UserRepository;
import com.project.ricky.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("유저메일 ================" + username);
        return userRepository.findByUserEmail(username).orElseThrow(()->new IllegalArgumentException(username+" 사용자가 존재하지 않습니다"));
    }
}
