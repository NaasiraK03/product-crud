package org.example.productcrud.service;

import jakarta.validation.Valid;
import org.example.productcrud.dto.ProductRequestDTO;
import org.example.productcrud.dto.ProductResponseDTO;
import org.example.productcrud.entity.Product;
import org.example.productcrud.exception.ProductNotFoundException;
import org.example.productcrud.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
   private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setQuantity(product.getQuantity());
        return productResponseDTO;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = toEntity(dto);
        Product savedProduct = productRepository.save(product);
        logger.info("Product created with id:{}",savedProduct.getId());
        return toResponseDTO(savedProduct);
    }

    public ProductResponseDTO getById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return toResponseDTO(product);
    }

    public Page<ProductResponseDTO> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Product> productPage =productRepository.findAll(pageable);
        return productPage.map(this::toResponseDTO);
    }

    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO dto) {

        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
        Product product = toEntity(dto);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        logger.info("Product updated with id: {}", id);
        return toResponseDTO(updatedProduct);
    }

    public ProductResponseDTO deleteById(Integer id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found to delete"));
        productRepository.delete(product);
        logger.info("Product deleted with id: {}", id);
        return toResponseDTO(product);

    }

}
