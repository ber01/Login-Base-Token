package me.kyunghwan.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class JwtApplicationTests {

    @Test
    void contextLoads() {
        JwtApplication.main(new String[]{});
    }

}
