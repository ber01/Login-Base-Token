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
        String name = "name";
        String picture = "picture_link";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .name(name)
                .picture(picture)
                .build();

        assertThat(account).isNotNull();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo(password);
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getPicture()).isEqualTo(picture);
    }

}