package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.Order;
import hr.algebra.thewineboutique.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> searchOrders(String customer, LocalDate fromDate, LocalDate toDate, BigDecimal minPrice, BigDecimal maxPrice) {
        return orderRepository.searchOrders(customer, fromDate, toDate, minPrice, maxPrice);
    }

    public List<Order> searchOrdersByUser(ApplicationUser user, String customer, LocalDate fromDate, LocalDate toDate, BigDecimal minPrice, BigDecimal maxPrice) {
        return orderRepository.searchOrdersByUser(user, customer, fromDate, toDate, minPrice, maxPrice);
    }

    public List<Order> getOrdersByUser(ApplicationUser user) {
        return orderRepository.findByUser(user);
    }
}

