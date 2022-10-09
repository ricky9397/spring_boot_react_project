package com.project.ricky.user.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDetail implements UserDetails {

    private User user;

    public UserDetail(User user) {
        this.user = user;
    }

    // pk값
    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김여부
    @Override
    public boolean isAccountNonLocked() {
        // userLockedYn = Y일 경우 계정락 걸려있음.
        return user.getLockedYn().equals("N") ? true : false;
    }

    // 비밀번호 만료여부.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성화 여부. true: 활성, false : 비활성
    @Override
    public boolean isEnabled() {
        return true;
    }
}
