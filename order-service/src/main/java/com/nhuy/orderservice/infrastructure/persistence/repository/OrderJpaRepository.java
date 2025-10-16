package com.nhuy.orderservice.infrastructure.persistence.repository;

import com.nhuy.orderservice.infrastructure.persistence.jpa.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
