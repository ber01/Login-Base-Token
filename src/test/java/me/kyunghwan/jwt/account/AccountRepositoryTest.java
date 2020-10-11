package me.kyunghwan.jwt.account;

import me.kyunghwan.jwt.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends BaseTest {

    @DisplayName("Account 저장 테스트")
    @Test
    public void saveTest() {
        String email = "email@email.com";
        String password = "1q2w3e4r5t!";

        Account account = accountRepository.save(Account.builder()
                .email(email)
                .password(password)
                .build());

        assertThat(accountRepository.existsByEmail(email)).isTrue();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo(password);
    }

}