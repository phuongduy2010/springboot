package com.nhuy.orderservice.api.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderDto(UUID customerId, List<Item> items) {
    public record Item(String productId, int quantity, BigDecimal unitPrice) {
    }
}
