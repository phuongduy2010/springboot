package com.nhuy.productservice.applicaton.service;

import java.math.BigDecimal;

public record PlaceProductCommand(String name, String description, BigDecimal price){
}