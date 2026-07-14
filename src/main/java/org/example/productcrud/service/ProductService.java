package org.example.productcrud.service;

import org.example.productcrud.entity.Product;
import org.example.productcrud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        Product newproduct = productRepository.save(product);
        return newproduct;
    }

    public Product getById(Integer id) {
        Optional<Product> found = productRepository.findById(id);
        return found.orElse(null);

    }

    public List<Product> getAll() {
        List<Product> productList = productRepository.findAll();
        return productList;

    }

    public boolean deleteById(Integer id) {
        boolean idExits = productRepository.existsById(id);
        if (idExits) {
            productRepository.deleteById(id);
            return true;
        } else {
            System.out.println("Given ID not available to delete");
            return false;
        }
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        boolean idExists = productRepository.existsById(id);
        if (idExists) {
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
        } else {
            System.out.println("ID not available to update");
        }
        return updatedProduct;
    }


}
