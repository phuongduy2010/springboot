package com.nhuy.orderservice.infrastructure.persistence.jpa;

import com.nhuy.orderservice.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {
    @Id
    private UUID id;
    private UUID customerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<OrderItemEntity> items = new ArrayList<>();
}
