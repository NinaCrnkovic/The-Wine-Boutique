package hr.algebra.thewineboutique.controller.mvc;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.algebra.thewineboutique.model.*;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.OrderService;
import hr.algebra.thewineboutique.service.PayPalService;
import hr.algebra.thewineboutique.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/TheWineBoutique")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserDetailsServiceImpl userService;
    private final CartService cartService;
    private final PayPalService payPalService;

    @PostMapping("/order/submit")
    public String submitOrder(@RequestParam("paymentMethod") PaymentMethod paymentMethod, HttpSession session, Model model) {
        ApplicationUser user = userService.getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItems(session.getId());
        BigDecimal totalPrice = cartService.getTotalPrice(session.getId());

        Cart existingCart = cartService.getCartBySessionId(session.getId());
        if (existingCart == null) {
            existingCart = new Cart(session.getId());
        }

        Order order = new Order();
        order.setUser(user);
        order.setCart(existingCart);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(paymentMethod);

        orderService.saveOrder(order);
        cartService.clearCart(session.getId());
        session.removeAttribute("cartItems");

        session.setAttribute("orderId", order.getId());

        if (paymentMethod == PaymentMethod.PAYPAL) {
            model.addAttribute("order", order);
            return "redirect:/TheWineBoutique/paypal";
        }

        return "redirect:/TheWineBoutique/orderConfirmation?orderId=" + order.getId();
    }

    @GetMapping("/orderConfirmation")
    public String orderConfirmation(@RequestParam("orderId") Integer orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "orderConfirmation";
    }

    @GetMapping("/paypal")
    public String paypalPage(HttpSession session, Model model) {
        ApplicationUser user = userService.getCurrentUser();
        Order order = orderService.getOrderById((Integer) session.getAttribute("orderId"));

        if (order != null) {
            try {
                String cancelUrl = "http://localhost:8080/TheWineBoutique/cancel";
                String successUrl = "http://localhost:8080/TheWineBoutique/success";
                Payment payment = payPalService.createPayment(order.getTotalPrice().doubleValue(), "USD", "paypal",
                        "sale", "Order Payment", cancelUrl, successUrl);
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }
}
