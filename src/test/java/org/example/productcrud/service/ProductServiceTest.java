package org.example.productcrud.service;

import org.example.productcrud.dto.ProductRequestDTO;
import org.example.productcrud.dto.ProductResponseDTO;
import org.example.productcrud.entity.Product;
import org.example.productcrud.exception.ProductNotFoundException;
import org.example.productcrud.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    ProductService productService;

    @Test
    void getById_shouldReturnProduct_whenProductExists() {
        Product product = new Product(1, "Speaker", 3000.0, 2);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getById(1);
        assertEquals("Speaker", result.getName());
        assertEquals(1, result.getId());

    }


    @Test
    public void getById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById(999));
    }

    @Test
    public void createProduct_shouldReturnSavedProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("Speaker", 3000.0, 2);
        Product savedProduct = new Product(1, "Speaker", 3000.0, 2);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        ProductResponseDTO result = productService.createProduct(dto);
        assertEquals(1, result.getId());
        assertEquals("Speaker", result.getName());
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
        // 1. Arrange: Configure the mock repo to find the product
        Product someProduct = new Product(1, "Speaker", 3000.0, 2); // Ensure this object exists
        when(productRepository.findById(1)).thenReturn(Optional.of(someProduct));

        // 2. Act: Call the service method
        productService.deleteById(1);

        // 3. Assert: Verify the repository's delete method was actually called
        verify(productRepository, times(1)).delete(someProduct);
        // verify(productRepository, times(1)).deleteById(88);
    }


}