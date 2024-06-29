package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.Cart;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findBySessionId(String sessionId);

    CartItem findByWineAndSessionId(Wine wine, String sessionId);

    CartItem findByIdAndSessionId(Integer id, String sessionId);
}
