package me.kyunghwan.jwt.oauth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GoogleOAuthRequestTest {

    @DisplayName("GoogleOAuthRequest 생성 테스트")
    @Test
    void javaBean() {
        String redirectUri = "redirectUri";
        String clientId = "clientId";
        String clientSecret = "clientSecret";
        String code = "code";
        String responseType = "responseType";
        String scope = "scope";
        String accessType = "accessType";
        String grantType = "grantType";
        String state = "state";
        String includeGrantedScopes = "includeGrantedScopes";
        String loginHint = "loginHint";
        String prompt = "prompt";

        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest.builder()
                .redirectUri(redirectUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .responseType(responseType)
                .scope(scope)
                .accessType(accessType)
                .grantType(grantType)
                .state(state)
                .includeGrantedScopes(includeGrantedScopes)
                .loginHint(loginHint)
                .prompt(prompt)
                .build();

        assertThat(googleOAuthRequest.toString()).isNotNull();

        assertThat(googleOAuthRequest.getRedirectUri()).isEqualTo(redirectUri);
        assertThat(googleOAuthRequest.getClientId()).isEqualTo(clientId);
        assertThat(googleOAuthRequest.getClientSecret()).isEqualTo(clientSecret);
        assertThat(googleOAuthRequest.getCode()).isEqualTo(code);
        assertThat(googleOAuthRequest.getResponseType()).isEqualTo(responseType);
        assertThat(googleOAuthRequest.getScope()).isEqualTo(scope);
        assertThat(googleOAuthRequest.getAccessType()).isEqualTo(accessType);
        assertThat(googleOAuthRequest.getGrantType()).isEqualTo(grantType);
        assertThat(googleOAuthRequest.getState()).isEqualTo(state);
        assertThat(googleOAuthRequest.getIncludeGrantedScopes()).isEqualTo(includeGrantedScopes);
        assertThat(googleOAuthRequest.getLoginHint()).isEqualTo(loginHint);
        assertThat(googleOAuthRequest.getPrompt()).isEqualTo(prompt);

        redirectUri = "MredirectUri";
        clientId = "MclientId";
        clientSecret = "MclientSecret";
        code = "Mcode";
        responseType = "MresponseType";
        scope = "Mscope";
        accessType = "MaccessType";
        grantType = "MgrantType";
        state = "Mstate";
        includeGrantedScopes = "MincludeGrantedScopes";
        loginHint = "MloginHint";
        prompt = "Mprompt";

        googleOAuthRequest.setRedirectUri(redirectUri);
        googleOAuthRequest.setClientId(clientId);
        googleOAuthRequest.setClientSecret(clientSecret);
        googleOAuthRequest.setCode(code);
        googleOAuthRequest.setResponseType(responseType);
        googleOAuthRequest.setScope(scope);
        googleOAuthRequest.setAccessType(accessType);
        googleOAuthRequest.setGrantType(grantType);
        googleOAuthRequest.setState(state);
        googleOAuthRequest.setIncludeGrantedScopes(includeGrantedScopes);
        googleOAuthRequest.setLoginHint(loginHint);
        googleOAuthRequest.setPrompt(prompt);

        assertThat(googleOAuthRequest.getRedirectUri()).isEqualTo(redirectUri);
        assertThat(googleOAuthRequest.getClientId()).isEqualTo(clientId);
        assertThat(googleOAuthRequest.getClientSecret()).isEqualTo(clientSecret);
        assertThat(googleOAuthRequest.getCode()).isEqualTo(code);
        assertThat(googleOAuthRequest.getResponseType()).isEqualTo(responseType);
        assertThat(googleOAuthRequest.getScope()).isEqualTo(scope);
        assertThat(googleOAuthRequest.getAccessType()).isEqualTo(accessType);
        assertThat(googleOAuthRequest.getGrantType()).isEqualTo(grantType);
        assertThat(googleOAuthRequest.getState()).isEqualTo(state);
        assertThat(googleOAuthRequest.getIncludeGrantedScopes()).isEqualTo(includeGrantedScopes);
        assertThat(googleOAuthRequest.getLoginHint()).isEqualTo(loginHint);
        assertThat(googleOAuthRequest.getPrompt()).isEqualTo(prompt);
    }

}