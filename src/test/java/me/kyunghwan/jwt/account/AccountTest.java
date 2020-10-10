package me.kyunghwan.jwt.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @DisplayName("Account 생성 테스트")
    @Test
    void beanTest() {
        String email = "email@email.com";
        String password = "password";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .build();

        assertThat(account).isNotNull();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo(password);
    }

}