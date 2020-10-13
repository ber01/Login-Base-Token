package me.kyunghwan.jwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.kyunghwan.jwt.BaseTest;
import me.kyunghwan.jwt.account.Account;
import me.kyunghwan.jwt.account.AccountAdapter;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwtTokenProviderTest extends BaseTest {

    @DisplayName("JwtToken 발급 값과 생성 값이 다른 테스트")
    @Test
    void requestTokenNeqCreateToken() throws Exception {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        String loginRequestJwtToken = getLoginRequestJwtToken(email, password);
        Thread.sleep(1000);
        String createJwtToken = getCreateJwtToken(account);

        assertThat(loginRequestJwtToken).isNotEqualTo(createJwtToken);
    }

    private String getCreateJwtToken(Account account) {
        AccountAdapter accountAdapter = new AccountAdapter(account);
        return jwtTokenProvider.createToken(accountAdapter.getUsername(), accountAdapter.getAuthorities());
    }

    private String getLoginRequestJwtToken(String email, String password) throws Exception {
        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password(password)
                .build();

        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        return parseToken(resultActions.andReturn().getResponse().getContentAsString());
    }

    private String parseToken(String token) {
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> map = jsonParser.parseMap(token);
        return ((String) map.get("message")).substring(7);
    }

    @DisplayName("Authentication 객체를 생성하는 테스트")
    @Test
    void getAuthentication() {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        String createJwtToken = getCreateJwtToken(account);
        Authentication authentication = jwtTokenProvider.getAuthentication(createJwtToken);

        System.out.println(authentication);

        assertThat(authentication).isNotNull();
    }

    @DisplayName("Email 을 JwtToken 에서 가져오는 테스트")
    @Test
    void getUsername() {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        String createJwtToken = getCreateJwtToken(account);
        String username = jwtTokenProvider.getUsername(createJwtToken);

        assertThat(email).isEqualTo(username);
    }

    @DisplayName("Request Header 에 Bearer 가 있는지 테스트")
    @Test
    public void requestContainsBearer() throws Exception {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        MockHttpServletRequest request = getRequest(account);
        String resolveToken = jwtTokenProvider.resolveToken(request);

        assertThat(resolveToken).contains("Bearer");
    }

    private MockHttpServletRequest getRequest(Account account) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/accounts/" + account.getIdx())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getCreateJwtToken(account)))
                .andDo(print())
                .andExpect(status().isOk());

        return resultActions.andReturn().getRequest();
    }

    @DisplayName("Exipre Token 테스트")
    @Test
    public void expireToken() throws Exception {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        String createExpireToken = getCreateExpireToken(account);

        Thread.sleep(3);

        mockMvc.perform(get("/api/accounts/" + account.getIdx())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + createExpireToken))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    private String getCreateExpireToken(Account account) {
        Claims claims = Jwts.claims().setSubject(account.getEmail());
        claims.put("roles", new AccountAdapter(account).getAuthorities());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 1000L))
                .signWith(SignatureAlgorithm.HS256, "MinKH")
                .compact();
    }


}