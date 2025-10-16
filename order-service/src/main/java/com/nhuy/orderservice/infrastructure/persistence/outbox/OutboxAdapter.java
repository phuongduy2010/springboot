package com.nhuy.orderservice.infrastructure.persistence.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuy.orderservice.application.port.OutboxPort;
import com.nhuy.orderservice.domain.model.event.OrderPlaced;
import com.nhuy.orderservice.infrastructure.persistence.jpa.OutboxEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxAdapter implements OutboxPort {
    private final OutboxJpaRepository repo;
    private final ObjectMapper om;
    @Override
    public void enqueue(OrderPlaced order) {
        var e = new OutboxEntity();
        e.setAggregateType("Order");

        var orderId = order.orderId();
        e.setAggregateId(orderId);
        e.setEventType(order.getClass().getSimpleName());
        try {
            e.setPayload(om.writeValueAsString(order));
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        e.setOccurredAt(OffsetDateTime.now());
        e.setProcessed(false);
        e.setDedupKey(UUID.randomUUID().toString());
        repo.save(e);

    }
}
