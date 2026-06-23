package com.applyhub.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 액세스_토큰을_생성하고_검증할_수_있다() {
        Long userId = 1L;

        String token = jwtTokenProvider.createAccessToken(userId);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void 토큰에서_사용자_ID를_추출할_수_있다() {
        Long userId = 1L;
        String token = jwtTokenProvider.createAccessToken(userId);

        Long tokenUserId = jwtTokenProvider.getUserId(token);

        assertThat(tokenUserId).isEqualTo(userId);
    }

    @Test
    void 유효하지_않은_토큰은_검증에_실패한다() {
        String invalidToken = "invalid-token";

        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        assertThat(isValid).isFalse();
    }
}