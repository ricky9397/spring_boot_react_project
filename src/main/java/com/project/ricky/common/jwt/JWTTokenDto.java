package com.project.ricky.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JWTTokenDto {

    private String authToken;
    private String refreshToken;


}
