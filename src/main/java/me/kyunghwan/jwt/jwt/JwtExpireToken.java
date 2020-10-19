package me.kyunghwan.jwt.jwt;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class JwtExpireToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column @NotNull
    private String jwtToken;

    @Column @NotNull
    private LocalDateTime date;

}
