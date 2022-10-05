package com.project.ricky.common.utils;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class Constants {

    public static final LocalDateTime REGDATE = LocalDateTime.now();
    public static final LocalDateTime MODDATE = LocalDateTime.now();
    public static final LocalDateTime LOGINDATE = LocalDateTime.now();
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String YES = "Y";
    public static final String NO = "N";

    public static final String REFRESH_TOKEN = "refresh_token"; // 리플래쉬 토큰
    public static final String AUTH_TOKEN = "auth_token"; // auth토큰


}
