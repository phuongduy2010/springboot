package com.nhuy.orderservice.application.port;

import com.nhuy.orderservice.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;
public interface OrderRepositoryPort {
    void save(Order order);
    Order find(UUID id);
    void update(Order order);
    List<Order> findAll(Pageable pageable);

    List<Order> findByCustomerId(UUID customerId, Pageable pageable);
}
