package com.project.ricky.common.Security.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {

    private Long userId;
    private String userEmail;
    private String userPassword;
    private String refreshToken;
    private boolean rememberme;

}
