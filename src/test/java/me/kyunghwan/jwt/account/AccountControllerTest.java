package me.kyunghwan.jwt.account;

import me.kyunghwan.jwt.BaseTest;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends BaseTest {

    @DisplayName("POST /api/accounts 201")
    @Test
    void postAccounts201() throws Exception {
        String email = "test@email.com";
        String password = "1q2w3e4r5t!";
        String name = "name";
        String picture = "picture";

        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password(password)
                .name(name)
                .picture(picture)
                .build();

        mockMvc.perform(post("/api/accounts")
                    .content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idx").exists())
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("picture").value(picture))
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/accounts"))
        ;
    }

    @DisplayName("POST /api/accounts 400")
    @ParameterizedTest(name = "#{index} : {4}")
    @MethodSource("params")
    void postAccounts400(String email, String password, String name, String picture, String message) throws Exception {
        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password(password)
                .name(name)
                .picture(picture)
                .build();

        mockMvc.perform(post("/api/accounts")
                    .content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Bad Request"))
        ;
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("email", "1q2w3e4r5t!", null, null, "이메일 형식 오류 - @ 없음"),
                Arguments.of("", "1q2w3e4r5t!", null, null, "이메일 형식 오류 - empty"),
                Arguments.of(" ", "1q2w3e4r5t!", null, null, "이메일 형식 오류 - 공백"),
                Arguments.of(null, "1q2w3e4r5t!", null, null, "이메일 형식 오류 - null"),
                Arguments.of("email.com", "!1234A", null, null, "비밀번호 형식 오류 - 길이 8 미만"),
                Arguments.of("email.com", "!1234567890987654321A", null, null, "비밀번호 형식 오류 - 길이 16 초과"),
                Arguments.of("email.com", "aaaaa!!!!!", null, null, "비밀번호 형식 오류 - 숫자 X"),
                Arguments.of("email.com", "12345!!!!!", null, null, "비밀번호 형식 오류 - 대소문자 X"),
                Arguments.of("email.com", "aaaaa12345", null, null, "비밀번호 형식 오류 - 특수문자 X"),
                Arguments.of("email.com", "!!!!!!!!!!", null, null, "비밀번호 형식 오류 - 숫자 X, 대소문자 X"),
                Arguments.of("email.com", "aaaaAAAAAA", null, null, "비밀번호 형식 오류 - 숫자 X, 특수문자 X"),
                Arguments.of("email.com", "1234123412", null, null, "비밀번호 형식 오류 - 대소문자 X, 특수문자 X")
        );
    }

    @DisplayName("GET /api/accounts/idx 401")
    @Test
    void getAccountsIdx401() throws Exception {
        String email = "email@email.com";
        String password = "password1!";
        Account account = saveAccount(email, password);

        Long idx = account.getIdx();

        mockMvc.perform(get("/api/accounts/" + idx))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @DisplayName("GET /api/accounts/idx 200")
    @Test
    void getAccountsIdx200() throws Exception {
        String email = "email@email.com";
        String password = "password1!";

        Account account = saveAccount(email, password);

        Long idx = account.getIdx();
        mockMvc.perform(get("/api/accounts/" + idx)
                    .header(HttpHeaders.AUTHORIZATION, createJwtToken(account)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("idx").value(account.getIdx()))
                .andExpect(jsonPath("email").value(account.getEmail()))
                .andExpect(jsonPath("name").value(account.getName()))
                .andExpect(jsonPath("picture").value(account.getPicture()))
        ;
    }

    private String createJwtToken(Account account) {
        AccountAdapter accountAdapter = new AccountAdapter(account);
        return "Bearer " + jwtTokenProvider.createToken(accountAdapter.getUsername(), accountAdapter.getAuthorities());
    }

    @DisplayName("GET /api/accounts/idx 400")
    @Test
    void getAccountsIdx400() throws Exception {
        String email = "email@email.com";
        String password = "password1!";

        Account account = saveAccount(email, password);

        long idx = 100L;

        mockMvc.perform(get("/api/accounts/" + idx)
                    .header(HttpHeaders.AUTHORIZATION, createJwtToken(account)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Bad Request"))
        ;
    }

}