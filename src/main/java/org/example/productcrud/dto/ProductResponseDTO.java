package org.example.productcrud.dto;


import java.io.Serializable;

public record ProductResponseDTO(Integer id, String name, Double price, Integer quantity, String categoryName) implements Serializable {
}
