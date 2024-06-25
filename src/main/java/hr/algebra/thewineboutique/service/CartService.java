package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.model.Wine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final Cart cart;



    public int getCartSize() {
        return cart.getItems().size();
    }

    public void addToCart(Wine wine, int quantity) {
        cart.addItem(wine, quantity);
    }

    public void removeFromCart(Long itemId) {
        cart.removeItem(itemId);
    }

    public void clearCart() {
        cart.clear();
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }
}
