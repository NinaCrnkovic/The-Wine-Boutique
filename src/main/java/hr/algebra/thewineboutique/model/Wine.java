package hr.algebra.thewineboutique.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "WINE")

public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    private WineCategoryEnum category;
    @Column(name = "type")
    private String type;
    @Column(name = "vintage")
    private String vintage;
    @Column(name = "country")
    private String country;
    @Column(name = "winery")
    private String winery;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "imageUrl")
    private String imageUrl;


    public Wine(String name, String description, WineCategoryEnum category, String type, String vintage, String country, String winery, BigDecimal price, Integer quantity, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.vintage = vintage;
        this.country = country;
        this.winery = winery;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
}
