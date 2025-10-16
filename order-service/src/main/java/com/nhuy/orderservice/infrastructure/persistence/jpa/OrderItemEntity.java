package com.nhuy.orderservice.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private int quantity;
    private BigDecimal unitPrice;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private OrderEntity order;
}
