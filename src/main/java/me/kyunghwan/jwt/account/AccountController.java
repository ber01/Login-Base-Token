package me.kyunghwan.jwt.account;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<?> postSignUp(@RequestBody AccountDTO accountDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Account saveAccount = accountRepository.save(accountDTO.toEntity());

        WebMvcLinkBuilder self = linkTo(AccountController.class).slash("/sign-up");
        EntityModel<Account> entityModel = EntityModel.of(saveAccount)
                .add(self.withSelfRel());

        return ResponseEntity.created(self.toUri()).body(entityModel);
    }

}
