package com.nhuy.productservice.infrastructure.persistence;

import com.nhuy.productservice.domain.model.Product;
import com.nhuy.productservice.applicaton.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor

public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductMongoRepository productMongoRepository;
    @Override
    public void createProduct(Product product) {
        ProductEntity productEntity = ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
        productMongoRepository.save(productEntity);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMongoRepository.findAll().stream()
                .map(this::mapToProduct).toList();
    }

    private Product mapToProduct(ProductEntity productEntity) {
        return  Product.builder()
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .id(productEntity.getId())
                .description(productEntity.getDescription())
                .build();
    }
}
