package com.nhuy.orderservice.infrastructure.mapper;

import com.nhuy.orderservice.domain.model.Order;
import com.nhuy.orderservice.infrastructure.persistence.jpa.OrderEntity;

public interface OrderMapper {
    OrderEntity toEntity(Order order);
    Order toDomain(OrderEntity entity);
}
