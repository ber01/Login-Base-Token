package me.kyunghwan.jwt.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import me.kyunghwan.jwt.account.Account;
import me.kyunghwan.jwt.account.AccountAdapter;
import me.kyunghwan.jwt.account.AccountRepository;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import me.kyunghwan.jwt.jwt.JwtTokenProvider;
import me.kyunghwan.jwt.oauth.dto.GoogleOAuthRequest;
import me.kyunghwan.jwt.oauth.dto.GoogleOAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final AccountRepository accountRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${redirect-url}")
    private String redirectUrl;

    @GetMapping("/login/google/auth")
    public ResponseEntity<?> googleAuth(@RequestParam(value = "code") String authCode) throws JsonProcessingException {

        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri(redirectUrl)
                .grantType("authorization_code").build();

        RestTemplate restTemplate = new RestTemplate();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResponseEntity<String> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", googleOAuthRequestParam, String.class);

        GoogleOAuthResponse result = objectMapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {});

        String jwtToken = result.getIdToken();
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", jwtToken).encode().toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        Map<String,String> userInfo = objectMapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});

        return createJwtToken(userInfo);
    }

    private ResponseEntity<?> createJwtToken(Map<String, String> userInfo) {
        Account account = saveOrUpdate(userInfo);
        AccountAdapter accountAdapter = new AccountAdapter(account);
        String jwtToken = jwtTokenProvider.createToken(accountAdapter.getUsername(), accountAdapter.getAuthorities());
        return ResponseEntity.ok("{\"message\" : " + "\"Bearer " + jwtToken + "\"}");
    }

    private Account saveOrUpdate(Map<String, String> userInfo) {
        String name = userInfo.get("name");
        String email = userInfo.get("email");
        String picture = userInfo.get("picture");

        AccountDTO accountDTO = AccountDTO.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build();

        Account account = accountRepository.findByEmail(email)
                .map(entity -> entity.update(accountDTO.getName(), accountDTO.getPicture())).orElse(accountDTO.toEntity());

        return accountRepository.save(account);
    }

}