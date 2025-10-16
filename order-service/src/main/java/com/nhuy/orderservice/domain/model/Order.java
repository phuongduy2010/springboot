package com.nhuy.orderservice.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  UUID id;
    private UUID customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private OffsetDateTime createdAt;

    public BigDecimal total() {
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void markAwaitingPayment() { this.status = OrderStatus.AWAITING_PAYMENT; }
    public void markConfirmed()       { this.status = OrderStatus.CONFIRMED; }
    public void markCancelled()       { this.status = OrderStatus.CANCELLED; }
}
