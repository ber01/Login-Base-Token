package me.kyunghwan.jwt.jwt;

import me.kyunghwan.jwt.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class JwtExpireTokenTest extends BaseTest {

    @Autowired
    JwtExpireTokenRepository jwtExpireTokenRepository;

    @DisplayName("JwtExpireToken 저장 테스트")
    @Test
    void jwtExpireTokenTest() {
        String jwtToken = "jwtToken";

        JwtExpireToken jwtExpireToken = jwtExpireTokenRepository.save(JwtExpireToken.builder()
                .jwtToken(jwtToken)
                .date(LocalDateTime.now())
                .build());

        assertThat(jwtExpireToken).isNotNull();
        assertThat(jwtExpireToken.getIdx()).isNotNull();
        assertThat(jwtExpireToken.getJwtToken()).isEqualTo(jwtToken);
        assertThat(jwtExpireToken.getDate()).isNotNull();

        assertThat(jwtExpireTokenRepository.existsByJwtToken(jwtToken)).isTrue();
    }


}