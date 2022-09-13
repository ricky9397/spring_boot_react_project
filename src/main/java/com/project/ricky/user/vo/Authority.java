package com.project.ricky.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_AUTHORITY")
@IdClass(Authority.class)
public class Authority implements GrantedAuthority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final Authority USER_AUTHORITY = Authority.builder().authority(ROLE_USER).build();
    public static final Authority ADMIN_AUTHORITY = Authority.builder().authority(ROLE_ADMIN).build();


    @Id
    private Long userId;

    @Id
    private String authority;

}
