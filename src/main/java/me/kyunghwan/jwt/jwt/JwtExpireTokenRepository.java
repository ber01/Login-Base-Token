package me.kyunghwan.jwt.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtExpireTokenRepository extends JpaRepository<JwtExpireToken, Long> {

    boolean existsByJwtToken(String jwtToken);

}
