package me.kyunghwan.jwt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "sign-in";
    }

}
