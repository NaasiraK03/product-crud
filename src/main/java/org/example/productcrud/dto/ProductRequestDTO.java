package org.example.productcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequestDTO {

    @NotBlank(message="Name is required")
   private String name;

    @NotNull(message="Price is required")
    @Positive(message="Price must be positive")
    private Double price;

    @NotNull(message = "Quantity required")
    @PositiveOrZero(message="Quantity cannot be negative")
    private Integer quantity;

    private Integer categoryId;

}
