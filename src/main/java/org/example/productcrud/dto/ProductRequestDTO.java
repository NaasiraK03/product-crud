package org.example.productcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductRequestDTO {

    @NotBlank(message="Name is required")
   private String name;

    @NotNull(message="Price is required")
    @Positive(message="Price must be positive")
    private Double price;

    @NotNull(message = "Quantity required")
    @PositiveOrZero(message="Quantity cannot be negative")
    private Integer quantity;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, Double price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
