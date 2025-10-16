package com.nhuy.orderservice.application.port;

import com.nhuy.orderservice.domain.model.Order;


import java.util.List;
import java.util.UUID;
public interface OrderRepositoryPort {
    void save(Order order);
    Order find(UUID id);
    void update(Order order);
    List<Order> findAll();
}
