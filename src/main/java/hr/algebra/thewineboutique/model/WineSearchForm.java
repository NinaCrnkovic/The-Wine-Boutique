package hr.algebra.thewineboutique.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WineSearchForm {
    private String name;
    private String description;
    private WineCategoryEnum category;
    private String type;
    private String vintage;
    private String country;
    private String winery;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
}
