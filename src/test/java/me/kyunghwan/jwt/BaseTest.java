package me.kyunghwan.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kyunghwan.jwt.account.Account;
import me.kyunghwan.jwt.account.AccountRepository;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import me.kyunghwan.jwt.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    protected Account saveAccount(String email, String password) {
        return accountRepository.save(AccountDTO.builder()
                .email(email)
                .password(password)
                .build()
                .toEntity(passwordEncoder));
    }

    @BeforeEach
    void deleteAll() {
        accountRepository.deleteAll();
    }

}
