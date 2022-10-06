package com.project.ricky.user.service;

import com.project.ricky.user.repository.UserRepository;
import com.project.ricky.user.vo.User;
import com.project.ricky.user.vo.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // 시큐리티 session(내부 Authentication(내부 UserDetails))
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserEmail(username).orElseThrow(()->new IllegalArgumentException(username+" 사용자가 존재하지 않습니다"));
        return new UserDetail(userEntity);
    }

    public void updateRefreshToken(String refreshToken, Long userId) {
        userRepository.update(refreshToken, userId); // 리플래쉬토큰 저장
    }

    public User findByRefreshToken(Long userId) {
        return userRepository.findByRefreshToken(userId);
    }
}
