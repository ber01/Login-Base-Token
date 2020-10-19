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
        assertThat(accountAdapter.isAccountNonExpired()).isTrue();
        assertThat(accountAdapter.isAccountNonLocked()).isTrue();
        assertThat(accountAdapter.isCredentialsNonExpired()).isTrue();
        assertThat(accountAdapter.isEnabled()).isTrue();
    }

    @DisplayName("Account Adapter 생성 테스트2")
    @Test
    void createAccountAdapter2() {
        String email = "test@email.com";

        Account account = Account.builder()
                .email(email)
                .build();

        AccountAdapter accountAdapter = new AccountAdapter(account);

        assertThat(accountAdapter.getAccount()).isEqualTo(account);
        assertThat(accountAdapter.getUsername()).isEqualTo(email);
        assertThat(accountAdapter.getPassword()).isNull();
        assertThat(accountAdapter.getAuthorities()).isNotNull();
    }

}