package me.kyunghwan.jwt.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true) @NotNull
    private String email;

    @Column
    private String password;

    private String name;

    private String picture;

    public Account update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

}
