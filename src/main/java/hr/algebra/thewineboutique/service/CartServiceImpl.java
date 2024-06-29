package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.repository.CartItemRepository;
import hr.algebra.thewineboutique.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(String sessionId, Wine wine, int quantity) {
        CartItem existingItem = cartItemRepository.findByWineAndSessionId(wine, sessionId);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setWine(wine);
            newItem.setSessionId(sessionId);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
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

    @Override
    public Cart getCartBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId);
    }
}