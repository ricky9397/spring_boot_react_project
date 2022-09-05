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

    private String username;
    private String password;
    private String site;
    private boolean rememberme;

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", site='" + site + '\'' +
                ", rememberme=" + rememberme +
                '}';
    }
}
