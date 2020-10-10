package me.kyunghwan.jwt.account;

import me.kyunghwan.jwt.BaseTest;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends BaseTest {

    @DisplayName("POST /api/accounts/sign-up [O]")
    @Test
    void postSignUp() throws Exception {
        AccountDTO dto = AccountDTO.builder()
                .email("test@email.com")
                .password("1q2w3e4r5t!")
                .build();

        mockMvc.perform(post("/api/accounts/sign-up")
                    .content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idx").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
    }

}