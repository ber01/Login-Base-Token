package me.kyunghwan.jwt;

import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends BaseTest {

    @DisplayName("POST /api/login 200")
    @Test
    void postLogin200() throws Exception {
        String email = "email@email.com";
        String password = "password1!";

        saveAccount(email, password);

        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("POST /api/login 400 - ID")
    @Test
    void postLoginEmail400() throws Exception {
        String email = "email@email.com";
        String password = "password1!";

        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/api/login")
                    .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @DisplayName("POST /api/login 400 - PWD")
    @Test
    void postLoginPassword400() throws Exception {
        String email = "email@email.com";
        String password = "password1!";

        saveAccount(email, password);

        AccountDTO dto = AccountDTO.builder()
                .email(email)
                .password("1q2w3e4r5t!")
                .build();

        mockMvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

}