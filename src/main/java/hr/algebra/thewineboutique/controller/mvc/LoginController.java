package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.publisher.CustomSpringEventPublisher;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/TheWineBoutique")
public class LoginController {

    private final CartService cartService;
    private final UserDetailsServiceImpl userService;
    private final CustomSpringEventPublisher publisher;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "customLogout";
    }


    @PostMapping("/login")
    public String handleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            ApplicationUser user = userService.authenticate(username, password);
            if (user != null) {
                mergeCartItems(session, user);
                publisher.publishCustomEvent("User logged in: " + username);
                return "redirect:/TheWineBoutique/wineStore";
            } else {
                return "redirect:/TheWineBoutique/login?error=true";
            }
        } catch (Exception e) {
            return "redirect:/TheWineBoutique/login?error=true";
        }
    }

    private void mergeCartItems(HttpSession session, ApplicationUser user) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems != null) {
            String sessionId = session.getId();
            for (CartItem item : cartItems) {
                cartService.addItemToCart(sessionId, item.getWine(), item.getQuantity());
            }
            session.removeAttribute("cartItems");
        }
    }
}