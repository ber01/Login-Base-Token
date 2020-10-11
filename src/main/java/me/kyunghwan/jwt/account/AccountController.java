package me.kyunghwan.jwt.account;

import lombok.RequiredArgsConstructor;
import me.kyunghwan.jwt.account.dto.AccountDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<?> postSignUp(@Valid @RequestBody AccountDTO accountDTO, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest();
        }

        Account saveAccount = accountRepository.save(accountDTO.toEntity());

        WebMvcLinkBuilder self = linkTo(AccountController.class);
        EntityModel<Account> entityModel = EntityModel.of(saveAccount)
                .add(self.withSelfRel());

        return ResponseEntity.created(self.toUri()).body(entityModel);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> getAccount(@PathVariable Long idx) {
        Optional<Account> optional = accountRepository.findById(idx);
        if (!optional.isPresent()) return badRequest();

        Account account = optional.get();
        WebMvcLinkBuilder self = linkTo(AccountController.class).slash(account.getIdx());
        EntityModel<Account> entityModel = EntityModel.of(account)
                .add(self.withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    private ResponseEntity<String> badRequest() {
        return ResponseEntity.badRequest().body("{\"message\" : \"잘못된 요청입니다.\"}");
    }

}
