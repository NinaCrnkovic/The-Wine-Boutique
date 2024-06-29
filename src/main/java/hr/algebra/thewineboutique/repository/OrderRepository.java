package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE "
            + "(:customer IS NULL OR o.user.username LIKE %:customer%) AND "
            + "(:fromDate IS NULL OR o.orderDate >= :fromDate) AND "
            + "(:toDate IS NULL OR o.orderDate <= :toDate) AND "
            + "(:minPrice IS NULL OR o.totalPrice >= :minPrice) AND "
            + "(:maxPrice IS NULL OR o.totalPrice <= :maxPrice)")
    List<Order> searchOrders(@Param("customer") String customer, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,
                             @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND "
            + "(:customer IS NULL OR o.user.username LIKE %:customer%) AND "
            + "(:fromDate IS NULL OR o.orderDate >= :fromDate) AND "
            + "(:toDate IS NULL OR o.orderDate <= :toDate) AND "
            + "(:minPrice IS NULL OR o.totalPrice >= :minPrice) AND "
            + "(:maxPrice IS NULL OR o.totalPrice <= :maxPrice)")
    List<Order> searchOrdersByUser(@Param("user") ApplicationUser user, @Param("customer") String customer, @Param("fromDate") LocalDate fromDate,
                                   @Param("toDate") LocalDate toDate, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);



    List<Order> findByUser(ApplicationUser user);

}
