package me.kyunghwan.jwt.account;

import me.kyunghwan.jwt.BaseTest;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends BaseTest {

    @DisplayName("POST /api/accounts Success")
    @Test
    void postSignUpSuccess() throws Exception {
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

    @DisplayName("POST /api/accounts Failure")
    @ParameterizedTest(name = "#{index} : {4}")
    @MethodSource("params")
    void postSignUpFailure(String email, String password, String name, String picture, String message) throws Exception {
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

    @DisplayName("GET /api/accounts/idx Success")
    @Test
    void getAccountsIdxSuccess() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("email@email.com")
                .password("password1!")
                .name("name")
                .picture("picture_link")
                .build());

        Long idx = account.getIdx();

        mockMvc.perform(get("/api/accounts/" + idx))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("idx").value(account.getIdx()))
                .andExpect(jsonPath("email").value(account.getEmail()))
                .andExpect(jsonPath("name").value(account.getName()))
                .andExpect(jsonPath("picture").value(account.getPicture()))
        ;
    }

    @DisplayName("GET /api/accounts/idx Failure")
    @Test
    void getAccountsIdxFailure() throws Exception {
        long idx = 100L;

        mockMvc.perform(get("/api/accounts/" + idx))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Bad Request"))
        ;
    }

}