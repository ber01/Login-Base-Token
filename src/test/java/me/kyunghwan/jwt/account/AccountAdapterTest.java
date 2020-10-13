package me.kyunghwan.jwt.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountAdapterTest {

    @DisplayName("Account Adapter 생성 테스트")
    @Test
    void createAccountAdapter() {
        String email = "test@email.com";
        String password = "1q2w3e4r!";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .build();

        AccountAdapter accountAdapter = new AccountAdapter(account);

        assertThat(accountAdapter.getAccount()).isEqualTo(account);
        assertThat(accountAdapter.getUsername()).isEqualTo(email);
        assertThat(accountAdapter.getPassword()).isEqualTo(password);
        assertThat(accountAdapter.getAuthorities()).isNotNull();
    }

}