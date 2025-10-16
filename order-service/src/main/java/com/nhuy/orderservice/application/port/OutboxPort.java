package com.nhuy.orderservice.application.port;

import com.nhuy.orderservice.domain.model.event.OrderPlaced;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxPort {
    void enqueue(OrderPlaced order);

}
