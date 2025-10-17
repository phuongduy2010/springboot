package com.nhuy.orderservice.application.handler;

import com.nhuy.orderservice.application.command.PlaceOrderCommand;
import com.nhuy.orderservice.application.port.OrderRepositoryPort;
import com.nhuy.orderservice.application.port.OutboxPort;
import com.nhuy.orderservice.domain.model.Order;
import com.nhuy.orderservice.domain.model.OrderItem;
import com.nhuy.orderservice.domain.model.OrderStatus;
import com.nhuy.orderservice.domain.model.event.OrderPlaced;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceOrderHandler {
    private final OrderRepositoryPort repository;
    private final OutboxPort outboxPort;
    public static final String ORDER_CACHE = "orders";
    @Transactional
    @CachePut(value = ORDER_CACHE, key="#result")
    public UUID handle(PlaceOrderCommand command){
        var orderId = UUID.randomUUID();
        var items = command.items().stream().map(
                item -> new OrderItem(item.productId(), item.qty(), item.unitPrice())
        ).toList();
        var order = new Order(orderId, command.customerId(), items, OrderStatus.PENDING, OffsetDateTime.now());
        repository.save(order);
        outboxPort.enqueue(new OrderPlaced(orderId, command.customerId(), OffsetDateTime.now(), items, 1));
        return  orderId;
    }
    @Cacheable(value = ORDER_CACHE, key = "#orderId")
    public Order getOrderById(UUID orderId){
        return repository.find(orderId);
    }

    public List<Order> getOrders(){
        return repository.findAll();
    }

}
