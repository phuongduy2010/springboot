package com.nhuy.orderservice.domain.model.event;

import com.nhuy.orderservice.domain.model.OrderItem;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderPlaced(UUID orderId, UUID customerId, OffsetDateTime occurredAt, List<OrderItem> items, int version) {
}
