package me.kyunghwan.jwt.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountAdapter extends User {

    private final Account account;

    public AccountAdapter(Account account) {
        super(account.getEmail(), password(account), authorities());
        this.account = account;
    }

    private static String password(Account entity) {
        return entity.getPassword() == null ? "google" : entity.getPassword();
    }

    private static Collection<? extends GrantedAuthority> authorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        return auth;
    }

    public Account getAccount() {
        return account;
    }
}
