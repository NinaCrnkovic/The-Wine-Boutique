package hr.algebra.thewineboutique.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "CART_ITEM")
public class CartItem {
    public CartItem(Wine wine, int quantity) {
        this.wine = wine;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "wine_id")
    private Wine wine;

    private int quantity;

    private String sessionId;

    public CartItem(String sessionId, Wine wine, int quantity) {
        this.sessionId = sessionId;
        this.wine = wine;
        this.quantity = quantity;

    }
}