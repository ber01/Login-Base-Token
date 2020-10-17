package me.kyunghwan.jwt.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.kyunghwan.jwt.oauth.dto.GoogleOAuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${redirect-url}")
    private String redirectUrl;

    @GetMapping("/login/google/auth")
    public String googleAuth(Model model, @RequestParam(value = "code") String authCode) throws JsonProcessingException {

        System.out.println(authCode);

        //HTTP Request를 위한 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Google OAuth Access Token 요청을 위한 파라미터 세팅
        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri(redirectUrl)
                .grantType("authorization_code").build();

        System.out.println(googleOAuthRequestParam);

        return "/sign-in";
    }

}