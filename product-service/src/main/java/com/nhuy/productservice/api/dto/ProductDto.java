package com.nhuy.productservice.api.dto;

import java.math.BigDecimal;

public record ProductDto(String name, String description, BigDecimal price){
}
