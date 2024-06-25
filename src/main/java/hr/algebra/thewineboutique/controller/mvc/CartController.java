package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.service.WineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final WineService wineService;

    public CartController(WineService wineService) {
        this.wineService = wineService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("wineId") int wineId, @RequestParam("quantity") int quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        Wine wine = wineService.findById(wineId);
        if (wine != null) {
            cart.addItem(wine, quantity);
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("itemId") Long itemId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItem(itemId);
        }
        return "redirect:/cart";
    }
}

