package org.example.productcrud.service;

import org.example.productcrud.dto.ProductRequestDTO;
import org.example.productcrud.dto.ProductResponseDTO;
import org.example.productcrud.entity.Product;
import org.example.productcrud.exception.ProductNotFoundException;
import org.example.productcrud.repository.CategoryRepository;
import org.example.productcrud.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.productcrud.entity.Category;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void getById_shouldReturnProduct_whenProductExists() {
        Category category = new Category(1, "Electronics", null);
        Product product = new Product(1, "Wireless Mouse", 799.99, 15, category);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getById(1);

        assertEquals("Wireless Mouse", result.name());
        assertEquals(1, result.id());
    }


    @Test
    public void getById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById(999));
    }

    @Test
    public void createProduct_shouldReturnSavedProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("Wireless Mouse", 799.99, 15, 1);
        Category category = new Category(1, "Electronics", null);
        Product savedProduct = new Product(1, "Wireless Mouse", 799.99, 15, category);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponseDTO result = productService.createProduct(dto);

        assertEquals(1, result.id());
        assertEquals("Wireless Mouse", result.name());
    }
    @Test
    public void deleteById_shouldThrowException_whenProductNotFound() {
        // 1. Arrange: Tell the repository to return an empty Optional when searching for the product
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // 2. Act & Assert: Execute the delete method and verify it throws the exception
        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(999));
    }



    @Test
    public void deleteById_shouldDeleteProduct_whenProductExists() {
        // 1. Arrange
        Category category = new Category(1, "Electronics", null);
        Product someProduct = new Product(1, "Wireless Mouse", 799.99, 15, category);
        when(productRepository.findById(1)).thenReturn(Optional.of(someProduct));

        // 2. Act
        productService.deleteById(1);

        // 3. Assert
        verify(productRepository, times(1)).delete(someProduct);
    }


}