package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findBySessionId(String sessionId);




    @Query("SELECT SUM(c.wine.price * c.quantity) FROM CartItem c WHERE c.cart.id = :cartId")
    BigDecimal calculateTotalPrice(@Param("cartId") Integer cartId);
}