package ShopAll.com.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password) {
        return "redirect:/E-commers";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "private";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccessPage() {
        return "login-success";
    }

    @GetMapping("/login-error")
    public String loginFailurePage() {
        return "login-error";
    }
    @GetMapping("/crudPersona")
    public String crudPersona() {
        return "personaCRUD";
    }
    @GetMapping("/e-commers")
    public String e_commers() {
        return "E-commers";
    }
}
