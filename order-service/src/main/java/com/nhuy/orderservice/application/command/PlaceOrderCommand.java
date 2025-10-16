package com.nhuy.orderservice.application.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PlaceOrderCommand (UUID customerId, List<Item> items) {
    public  record  Item(String productId, int qty,  BigDecimal unitPrice) {}
}
