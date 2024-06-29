package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findBySessionId(String sessionId);


}