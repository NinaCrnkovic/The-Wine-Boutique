package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@AllArgsConstructor
public abstract class BaseController {


    private CartService cartService;
    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        boolean isAdmin = isAuthenticated && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);

    }
    protected void handlePostLogin(HttpSession session, ApplicationUser user) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                cartService.addItemToCart(session.getId(), item.getWine(), item.getQuantity());
            }
            session.removeAttribute("cartItems");
        }
    }
}