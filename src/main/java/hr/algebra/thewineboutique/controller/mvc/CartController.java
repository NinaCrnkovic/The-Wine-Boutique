package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.*;
import hr.algebra.thewineboutique.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/TheWineBoutique")
public class CartController extends BaseController {

    private final CartService cartService;
    private final WineService wineService;
    private final UserDetailsServiceImpl userService;

    public CartController(CartService cartService, WineService wineService, UserDetailsServiceImpl userService) {
        super(cartService);
        this.cartService = cartService;
        this.wineService = wineService;
        this.userService = userService;
    }


        @PostMapping("/cart/add")
        public String addToCart(@RequestParam("wineId") Integer wineId, @RequestParam("quantity") int quantity, HttpSession session) {
            Wine wine = wineService.findById(wineId);
            cartService.addItemToCart(session.getId(), wine, quantity);
            saveCartToSession(session);
            return "redirect:/TheWineBoutique/cart/view";
        }

        @GetMapping("/cart/view")
        public String viewCart(HttpSession session, Model model) {
            loadCartFromSession(session);
            model.addAttribute("cartItems", cartService.getCartItems(session.getId()));
            model.addAttribute("totalPrice", cartService.getTotalPrice(session.getId()));
            return "cart";
        }

        @PostMapping("/cart/update")
        public String updateCart(@RequestParam("itemId") Integer itemId, @RequestParam("quantity") int quantity, HttpSession session) {
            cartService.updateItemQuantity(session.getId(), itemId, quantity);
            saveCartToSession(session);
            return "redirect:/TheWineBoutique/cart/view";
        }

        @PostMapping("/cart/remove/{itemId}")
        public String removeFromCart(@PathVariable("itemId") Integer itemId, HttpSession session) {
            cartService.removeItemFromCart(session.getId(), itemId);
            saveCartToSession(session);
            return "redirect:/TheWineBoutique/cart/view";
        }

        @PostMapping("/cart/clear")
        public String clearCart(HttpSession session) {
            cartService.clearCart(session.getId());
            session.removeAttribute("cartItems");
            return "redirect:/TheWineBoutique/cart/view";
        }

        @PostMapping("/cart/checkout")
        public String checkout(HttpSession session, Model model) {
            ApplicationUser user = userService.getCurrentUser();
            if (user == null) {
                saveCartToSession(session);
                return "redirect:/TheWineBoutique/loginPrompt";
            } else {
                loadCartFromSession(session);
                model.addAttribute("user", user);
                model.addAttribute("cartItems", cartService.getCartItems(session.getId()));
                model.addAttribute("totalPrice", cartService.getTotalPrice(session.getId()));
                model.addAttribute("orderDate", LocalDateTime.now());
                model.addAttribute("paymentMethods", PaymentMethod.values());
                return "order";
            }
        }

        private void saveCartToSession(HttpSession session) {
            List<CartItem> cartItems = cartService.getCartItems(session.getId());
            session.setAttribute("cartItems", cartItems);
        }

        private void loadCartFromSession(HttpSession session) {
            List<CartItem> sessionCartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (sessionCartItems != null) {
                List<CartItem> currentCartItems = cartService.getCartItems(session.getId());
                for (CartItem sessionItem : sessionCartItems) {
                    boolean itemExists = currentCartItems.stream()
                            .anyMatch(cartItem -> cartItem.getWine().getId().equals(sessionItem.getWine().getId()));
                    if (!itemExists) {
                        cartService.addItemToCart(session.getId(), sessionItem.getWine(), sessionItem.getQuantity());
                    }
                }
            }
        }

        @GetMapping("/loginPrompt")
        public String loginPrompt() {
            return "loginPrompt";
        }
    }
