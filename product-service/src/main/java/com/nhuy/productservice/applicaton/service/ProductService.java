package com.nhuy.productservice.applicaton.service;

import com.nhuy.productservice.domain.model.Product;
import com.nhuy.productservice.applicaton.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    public void createProduct(PlaceProductCommand productCommand){
        Product product = Product.builder()
                .name(productCommand.name())
                .description(productCommand.description())
                .price(productCommand.price())
                .build();
        productRepository.createProduct(product);
    }
}
