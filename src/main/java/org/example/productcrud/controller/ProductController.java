package org.example.productcrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productcrud.dto.ProductRequestDTO;
import org.example.productcrud.dto.ProductResponseDTO;
import org.example.productcrud.entity.Product;
import org.example.productcrud.service.ProductService;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping
    public PagedModel<ProductResponseDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int size) {
        return productService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ProductResponseDTO deleteById(@PathVariable Integer id) {
        return productService.deleteById(id);
    }

    @PutMapping("/{id}")
   public ProductResponseDTO updateById(@PathVariable Integer id, @Valid @RequestBody ProductRequestDTO updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

}
