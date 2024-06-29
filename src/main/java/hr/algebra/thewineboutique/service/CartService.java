package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.model.Wine;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;


public interface CartService {

    void addItemToCart(String sessionId, Wine wine, int quantity);

    List<CartItem> getCartItems(String sessionId);

    BigDecimal getTotalPrice(String sessionId);

    void removeItemFromCart(String sessionId, Integer itemId);

    void clearCart(String sessionId);
}
