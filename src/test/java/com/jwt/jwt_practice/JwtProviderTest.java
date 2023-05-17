package com.jwt.jwt_practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtProviderTest {

    @Autowired private JwtProvider jwtProvider;
    @Value("${custom.jwt.secretKey}")
    private String secretKeyPlain;

    @Test
    @DisplayName("accessToken 을 얻는다.")
    void t5() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");

        // 지금으로부터 5시간의 유효기간을 가지는 토큰을 생성
        String accessToken = jwtProvider.genToken(claims, 60 * 60 * 5);

        System.out.println("accessToken : " + accessToken);

        assertThat(accessToken).isNotNull();
    }

    @Test
    @DisplayName("accessToken 을 통해서 claims 를 얻을 수 있다.")
    void t6() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");

        // 지금으로부터 5시간의 유효기간을 가지는 토큰을 생성
        String accessToken = jwtProvider.genToken(claims, 60 * 60 * 5);

        System.out.println("accessToken : " + accessToken);

        assertThat(jwtProvider.verify(accessToken)).isTrue();

        Map<String, Object> claimsFromToken = jwtProvider.getClaims(accessToken);
        System.out.println("claimsFromToken : " + claimsFromToken);
    }

    @Test
    @DisplayName("만료된 토큰을 유효하지 않다.")
    void t7() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");

        // 지금으로부터 5시간의 유효기간을 가지는 토큰을 생성
        String accessToken = jwtProvider.genToken(claims, -1);

        System.out.println("accessToken : " + accessToken);

        assertThat(jwtProvider.verify(accessToken)).isFalse();
    }
}