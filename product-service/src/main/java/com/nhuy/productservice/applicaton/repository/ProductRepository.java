package com.nhuy.productservice.applicaton.repository;



import com.nhuy.productservice.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {
    void createProduct(Product product);
    public List<Product> getAllProducts();
}
