package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.publisher.CustomSpringEventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/TheWineBoutique")
public class LoginController {

    private CustomSpringEventPublisher publisher;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "customLogout";
    }


    @PostMapping("/login")
    public String handleLogin(HttpServletRequest request, HttpServletResponse response) {

        publisher.publishCustomEvent("User logged in: " + request.getUserPrincipal().getName());
        return "redirect:/home";
    }
}
