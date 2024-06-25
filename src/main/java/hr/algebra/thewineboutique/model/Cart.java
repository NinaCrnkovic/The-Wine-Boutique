package hr.algebra.thewineboutique.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    public void addItem(Wine wine, int quantity) {
        for (CartItem item : items) {
            if (item.getWine().getId().equals(wine.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(counter.incrementAndGet(), wine, quantity));
    }

    public void removeItem(Long itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    public void clear() {
        items.clear();
    }
}
