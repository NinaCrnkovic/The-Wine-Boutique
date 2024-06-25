package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.service.CartService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/TheWineBoutique")

public class HomeController extends BaseController{

    public HomeController(CartService cartService) {
        super(cartService);
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {

        return "home";
    }
}