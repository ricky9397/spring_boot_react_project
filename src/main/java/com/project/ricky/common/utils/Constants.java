package com.project.ricky.common.utils;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**+
 * Constants 상수
 */
public class Constants {

    public static final LocalDateTime REGDATE = LocalDateTime.now(); // 가입날짜.
    public static final LocalDateTime MODDATE = LocalDateTime.now(); // 수정날짜.
    public static final LocalDateTime LOGINDATE = LocalDateTime.now(); // 로그인시점날짜.
    public static final String ROLE_USER = "ROLE_USER"; // role 유저.
    public static final String ROLE_ADMIN = "ROLE_ADMIN"; // role 관리자.
    public static final String YES = "Y"; // 성공여부 ( 여부는 기초코드 생성 x )
    public static final String NO = "N"; // 실패여부 ( 여부는 기초코드 생성 x )
    public static final String REFRESH_TOKEN = "refresh_token"; // 리플래쉬 토큰
    public static final String AUTH_TOKEN = "auth_token"; // auth토큰

    public static final String Failed = "999"; // 실패
    public static final String SUCCESS = "200"; // 성공


}
