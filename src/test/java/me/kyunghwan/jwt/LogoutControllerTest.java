package me.kyunghwan.jwt;

import me.kyunghwan.jwt.account.Account;
import me.kyunghwan.jwt.account.AccountAdapter;
import me.kyunghwan.jwt.jwt.JwtExpireTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LogoutControllerTest extends BaseTest {

    @Autowired
    JwtExpireTokenRepository jwtExpireTokenRepository;

    @DisplayName("POST /api/logout 200")
    @Test
    void postLogout200() throws Exception {
        String email = "Logout@test.com";
        String password = "1q2w3e4r!";

        Account account = saveAccount(email, password);

        String jwtToken = createJwtToken(account);

        mockMvc.perform(post("/api/logout")
                    .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Logout Success"))
        ;

        mockMvc.perform(get("/api/accounts/" + account.getIdx())
                    .header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    private String createJwtToken(Account account) {
        AccountAdapter accountAdapter = new AccountAdapter(account);
        return "Bearer " + jwtTokenProvider.createToken(accountAdapter.getUsername(), accountAdapter.getAuthorities());
    }

}