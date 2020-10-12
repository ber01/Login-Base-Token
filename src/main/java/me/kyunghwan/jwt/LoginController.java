package me.kyunghwan.jwt;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.jwt.account.Account;
import me.kyunghwan.jwt.account.AccountRepository;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import me.kyunghwan.jwt.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/login")
@RestController
public class LoginController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AccountDTO accountDTO) {
        Optional<Account> optional = accountRepository.findByEmail(accountDTO.getEmail());
        if (!optional.isPresent()) return badRequest();

        Account account = optional.get();
        if (!passwordEncoder.matches(accountDTO.getPassword(), account.getPassword())) return badRequest();

        String jwtToken = jwtTokenProvider.createToken(account.getUsername(), account.getAuthorities());
        return ResponseEntity.ok("{\"message\" : " + "\"Bearer " + jwtToken + "\"}");
    }

    private ResponseEntity<String> badRequest() {
        return ResponseEntity.badRequest().body("{\"message\" : \"Bad Request\"}");
    }

}
