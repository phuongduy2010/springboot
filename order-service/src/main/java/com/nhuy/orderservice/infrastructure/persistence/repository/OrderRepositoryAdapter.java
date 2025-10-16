package com.nhuy.orderservice.infrastructure.persistence.repository;

import com.nhuy.orderservice.application.port.OrderRepositoryPort;
import com.nhuy.orderservice.domain.model.Order;
import com.nhuy.orderservice.infrastructure.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {
    private  final OrderJpaRepository orderJpaRepository;
    private  final OrderMapper orderMapper;

    @Transactional
    @Override
    public void save(Order order) {
        orderJpaRepository.save(orderMapper.toEntity(order));
    }

    @Override
    public Order find(UUID id) {
        return orderMapper.toDomain(orderJpaRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void update(Order order) {
        orderJpaRepository.save(orderMapper.toEntity(order));
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream().map(orderMapper::toDomain).toList();
    }
}
