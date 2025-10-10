package com.nhuy.productservice.api.controller;

import com.nhuy.productservice.api.dto.ProductDto;
import com.nhuy.productservice.api.dto.ProductResponse;
import com.nhuy.productservice.applicaton.service.PlaceProductCommand;
import com.nhuy.productservice.applicaton.service.ProductService;
import com.nhuy.productservice.domain.model.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDto productDTO){
        PlaceProductCommand productCommand = new PlaceProductCommand(
                productDTO.name(), productDTO.description(), productDTO.price()
        );
        productService.createProduct(productCommand);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        log.info(">> get all products");
        return productService.getAllProducts().stream().map(
                this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
