package org.example.productcrud.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productcrud.dto.ProductRequestDTO;
import org.example.productcrud.dto.ProductResponseDTO;
import org.example.productcrud.entity.Product;
import org.example.productcrud.exception.ProductNotFoundException;
import org.example.productcrud.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {

        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = toEntity(dto);
        Product savedProduct = productRepository.save(product);
        log.info("Product created with id:{}", savedProduct.getId());
        return toResponseDTO(savedProduct);
    }

    public ProductResponseDTO getById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return toResponseDTO(product);
    }

    public PagedModel<ProductResponseDTO> getAll(int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        // 1. Map to DTO Page
        Page<ProductResponseDTO> dtoPage = productPage.map(this::toResponseDTO);

        // 2. Wrap directly into Spring Data's PagedModel
        return new PagedModel<>(dtoPage);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO dto) {

        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
        Product product = toEntity(dto);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        log.info("Product updated with id: {}", id);
        return toResponseDTO(updatedProduct);
    }

    @Transactional
    public ProductResponseDTO deleteById(Integer id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found to delete"));
        productRepository.delete(product);
        log.info("Product deleted with id: {}", id);
        return toResponseDTO(product);

    }

}
