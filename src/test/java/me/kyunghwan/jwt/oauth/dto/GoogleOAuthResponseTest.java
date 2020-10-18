package me.kyunghwan.jwt.oauth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GoogleOAuthResponseTest {

    @DisplayName("GoogleOAuthResponse 생성 테스트")
    @Test
    void beanTest() {
        GoogleOAuthResponse googleOAuthResponse = new GoogleOAuthResponse();
        String accessToken = "accessToken";
        String expiresin = "expiresin";
        String idtoken = "idtoken";
        String refreshtoken = "refreshtoken";
        String scope = "scope";
        String tokentype = "tokentype";

        googleOAuthResponse.setAccessToken(accessToken);
        googleOAuthResponse.setExpiresIn(expiresin);
        googleOAuthResponse.setIdToken(idtoken);
        googleOAuthResponse.setRefreshToken(refreshtoken);
        googleOAuthResponse.setScope(scope);
        googleOAuthResponse.setTokenType(tokentype);

        assertThat(googleOAuthResponse.toString()).isNotNull();
        assertThat(googleOAuthResponse.getAccessToken()).isEqualTo(accessToken);
        assertThat(googleOAuthResponse.getExpiresIn()).isEqualTo(expiresin);
        assertThat(googleOAuthResponse.getIdToken()).isEqualTo(idtoken);
        assertThat(googleOAuthResponse.getRefreshToken()).isEqualTo(refreshtoken);
        assertThat(googleOAuthResponse.getScope()).isEqualTo(scope);
        assertThat(googleOAuthResponse.getTokenType()).isEqualTo(tokentype);
    }

}