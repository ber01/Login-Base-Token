package me.kyunghwan.jwt.account.dto;

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

}