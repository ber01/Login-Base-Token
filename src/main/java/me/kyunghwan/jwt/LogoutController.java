package me.kyunghwan.jwt;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.jwt.jwt.JwtExpireToken;
import me.kyunghwan.jwt.jwt.JwtExpireTokenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/logout")
@RestController
public class LogoutController {

    private final JwtExpireTokenRepository jwtExpireTokenRepository;

    @PostMapping
    public ResponseEntity<?> postLogout(HttpServletRequest request) {

        String jwtToken = request.getHeader("Authorization").substring(7);

        jwtExpireTokenRepository.save(JwtExpireToken.builder()
                .jwtToken(jwtToken)
                .date(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().body("{\"message\" : \"Logout Success\"}");
    }

}
