package com.project.ricky.common.Security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.ricky.user.dto.User;
import com.project.ricky.user.dto.UserDetail;

import java.time.Instant;

public class JWTUtil {

//    @Value("${jwt.secret}")
//    private static String secret;

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("secret");
    private static final long AUTH_TIME = 10;
    private static final long REFRESH_TIME = 60*60*24*7; // 7일

    public static String makeAuthToken(UserDetail userDetail) { // AuthToken 토큰발행
        // TODO: JJWT 사용! RSA방식은 아님 Hash암호방식
        return JWT.create()
                .withSubject(userDetail.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+AUTH_TIME)) // 어느시간이 지나면 로그인 x ( System.currentTimeMillis() = 현재시간 )
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeAuthToken(User user) { // AuthToken 토큰발행
        // TODO: JJWT 사용! RSA방식은 아님 Hash암호방식
        return JWT.create()
                .withSubject(user.getUserEmail())
//                .withExpiresAt(new Date(System.currentTimeMillis()+AUTH_TIME)) // 어느시간이 지나면 로그인 x ( System.currentTimeMillis() = 현재시간 )
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(UserDetail userDetail){ // RefreshToken 토큰발행
        return JWT.create()
                .withSubject(userDetail.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond()+REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(User user){ // RefreshToken 토큰발행
        return JWT.create()
                .withSubject(user.getUserEmail())
                .withClaim("exp", Instant.now().getEpochSecond()+REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token){
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder().success(true)
                    .username(verify.getSubject()).build();
        }catch(Exception ex){
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .username(decode.getSubject()).build();
        }
    }

}
