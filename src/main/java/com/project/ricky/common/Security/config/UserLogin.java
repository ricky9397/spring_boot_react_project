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

    private String userEmail;
    private String userPassword;
    private String site;
    private boolean rememberme;

    @Override
    public String toString() {
        return "UserLogin{" +
                "userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", site='" + site + '\'' +
                ", rememberme=" + rememberme +
                '}';
    }
}
