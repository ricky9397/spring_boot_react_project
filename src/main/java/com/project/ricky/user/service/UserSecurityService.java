package com.project.ricky.user.service;

import com.project.ricky.common.Utils;
import com.project.ricky.user.repository.UserRepository;
import com.project.ricky.user.vo.Authority;
import com.project.ricky.user.vo.User;
import com.project.ricky.user.vo.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

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
}
