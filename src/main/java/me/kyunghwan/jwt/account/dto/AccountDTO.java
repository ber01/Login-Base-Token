package me.kyunghwan.jwt.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kyunghwan.jwt.account.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountDTO {

    @Email @NotBlank
    private String email;

    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}") @NotBlank
    private String password;

    private String name;

    private String picture;

    public Account toEntity() {
        return Account.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .picture(this.picture)
                .build();
    }
}
