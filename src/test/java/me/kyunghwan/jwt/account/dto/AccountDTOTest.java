package me.kyunghwan.jwt.account.dto;

import me.kyunghwan.jwt.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDTOTest {

    @DisplayName("AccountDTO 생성 테스트")
    @Test
    public void beanTest() {
        String email = "email.com";
        String password = "123";

        AccountDTO accountDTO = AccountDTO.builder()
                .email(email)
                .password(password)
                .build();

        assertThat(accountDTO).isNotNull();
        assertThat(accountDTO.getEmail()).isEqualTo(email);
        assertThat(accountDTO.getPassword()).isEqualTo(password);
        assertThat(accountDTO.getName()).isNull();
        assertThat(accountDTO.getPicture()).isNull();
    }

    @DisplayName("AccountDTO to Account 테스트")
    @Test
    public void toEntity() {
        String email = "email.com";
        String password = "123";

        AccountDTO accountDTO = AccountDTO.builder()
                .email(email)
                .password(password)
                .build();

        Account account = accountDTO.toEntity();
        assertThat(account).isNotNull();
        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getPassword()).isEqualTo(password);
    }

}