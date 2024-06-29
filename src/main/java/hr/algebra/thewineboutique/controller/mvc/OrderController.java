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
import org.springframework.format.annotation.DateTimeFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller

@RequestMapping("/TheWineBoutique")
public class OrderController  extends BaseController {


    private OrderService orderService;

    private UserDetailsServiceImpl userService;

    private CartService cartService;

    private PayPalService payPalService;

    public OrderController(CartService cartService, OrderService orderService, UserDetailsServiceImpl userService, PayPalService payPalService) {
        super(cartService);
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.payPalService = payPalService;

    }

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
            return "paypal";
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
            model.addAttribute("order", order);
            return "paypal";
        }
        return "redirect:/";
    }

    @PostMapping("/paypal/execute")
    public String executePayPal(HttpSession session, Model model) {
        ApplicationUser user = userService.getCurrentUser();
        Order order = orderService.getOrderById((Integer) session.getAttribute("orderId"));

        if (order != null) {
            try {
                String cancelUrl = "http://localhost:8081/TheWineBoutique/cancel";
                String successUrl = "http://localhost:8081/TheWineBoutique/success";
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

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, HttpSession session) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // Retrieve the order
                Order order = orderService.getOrderById((Integer) session.getAttribute("orderId"));
                if (order != null) {
                    orderService.saveOrder(order); // Save the order
                }
                model.addAttribute("message", "Payment successful");
                return "paypalSuccess";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Payment failed");
        return "error";
    }

    @GetMapping("/cancel")
    public String cancelPay(Model model) {
        model.addAttribute("message", "Payment cancelled");
        return "paypalCancel";
    }

    @GetMapping("/orders")
    public String getAllOrders(
            @RequestParam(value = "customer", required = false) String customer,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Model model) {
        ApplicationUser user = userService.getCurrentUser();
        List<Order> orders;

        if (user.isAdmin()) {
            orders = orderService.searchOrders(customer, fromDate, toDate, minPrice, maxPrice);
        } else {
            orders = orderService.searchOrdersByUser(user, customer, fromDate, toDate, minPrice, maxPrice);
        }

        model.addAttribute("orders", orders);
        return "orders";
    }
}