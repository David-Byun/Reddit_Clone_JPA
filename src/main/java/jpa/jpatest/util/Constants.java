package jpa.jpatest.util;

import lombok.experimental.UtilityClass;

/*
    @UtilityClass 객체를 사용하면 생성자 private로 만들어주고 모든 메서드를 static으로 만들어줌
 */

@UtilityClass
public class Constants {
    public static final String ACTIVATION_EMAIL = "http://localhost:8084/api/auth/accountVerification";
}