package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.repository.CartItemRepository;
import hr.algebra.thewineboutique.repository.CartRepository;
import hr.algebra.thewineboutique.repository.WineRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    @Override
    public void addItemToCart(String sessionId, Wine wine, int quantity) {
        CartItem existingCartItem = cartItemRepository.findByWineAndSessionId(wine, sessionId);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem(sessionId, wine, quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItem> getCartItems(String sessionId) {
        return cartItemRepository.findBySessionId(sessionId);
    }

    @Override
    public BigDecimal getTotalPrice(String sessionId) {
        return cartItemRepository.findBySessionId(sessionId).stream()
                .map(item -> item.getWine().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void removeItemFromCart(String sessionId, Integer itemId) {
        CartItem cartItem = cartItemRepository.findByIdAndSessionId(itemId, sessionId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
    }


    @Override
    public void clearCart(String sessionId) {
        List<CartItem> cartItems = cartItemRepository.findBySessionId(sessionId);
        cartItemRepository.deleteAll(cartItems);
    }
}