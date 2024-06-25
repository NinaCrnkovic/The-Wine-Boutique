package hr.algebra.thewineboutique.dto;

import hr.algebra.thewineboutique.model.WineCategoryEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WineDTO {

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @NotEmpty(message = "Name must be entered")
    private String name;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @NotEmpty(message = "Description must be entered")
    private String description;
    @NotEmpty(message = "Category must be entered")
    private WineCategoryEnum category;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String type;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @NotEmpty(message = "Vintage must be entered")
    private String vintage;


    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @NotEmpty(message = "Country must be entered")
    private String country;
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @NotEmpty(message = "Region must be entered")
    private String winery;
    @DecimalMin(value = "0.00", message = "Price must be greater than 0")

    private BigDecimal price;
}
