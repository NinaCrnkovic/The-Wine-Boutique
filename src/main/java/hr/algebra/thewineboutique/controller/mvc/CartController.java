package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.WineService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/TheWineBoutique")

public class CartController extends BaseController{

    private final CartService cartService;
    private final WineService wineService;

    public CartController(CartService cartService, WineService wineService, CartService cartService1) {
        super(cartService);
        this.cartService = cartService1;
        this.wineService = wineService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("wineId") Integer wineId, @RequestParam("quantity") int quantity, HttpSession session) {
        Wine wine = wineService.findById(wineId);
        cartService.addItemToCart(session.getId(), wine, quantity);
        return "redirect:/TheWineBoutique/cart/view";
    }

    @GetMapping("/cart/view")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCartItems(session.getId()));
        model.addAttribute("totalPrice", cartService.getTotalPrice(session.getId()));

        return "cart";
    }

    @PostMapping("/cart/remove/{itemId}")
    public String removeFromCart(@PathVariable("itemId") Integer itemId, HttpSession session) {
        cartService.removeItemFromCart(session.getId(), itemId);
        return "redirect:/TheWineBoutique/cart/view";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session.getId());
        return "redirect:/TheWineBoutique/cart/view";
    }

    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session, Model model) {
        // Implement checkout logic here
        return "checkout";
    }
}