package com.nhuy.orderservice.infrastructure.mapper;

import com.nhuy.orderservice.domain.model.Order;
import com.nhuy.orderservice.domain.model.OrderItem;
import com.nhuy.orderservice.infrastructure.persistence.jpa.OrderEntity;
import com.nhuy.orderservice.infrastructure.persistence.jpa.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapperImplementation implements  OrderMapper{
    @Override
    public OrderEntity toEntity(Order order) {
        OrderEntity e = new OrderEntity();
        e.setId(order.getId());
        e.setCustomerId(order.getCustomerId());
        e.setStatus(order.getStatus());
        e.setCreatedAt(order.getCreatedAt());

        List<OrderItem> lines = order.getItems();
        List<OrderItemEntity> entityLines = new ArrayList<>(lines.size());
        for (OrderItem item : lines) {
            OrderItemEntity itemEntity =  orderLineToEntity(item);;
            itemEntity.setOrder(e);
            entityLines.add(itemEntity);
        }
        e.setItems(entityLines);
        return  e;
    }

    @Override
    public Order toDomain(OrderEntity e) {
        Order o = new Order();
        o.setId(e.getId());
        o.setCustomerId(e.getCustomerId());
        o.setStatus(e.getStatus());
        o.setCreatedAt(e.getCreatedAt());

        // map order lines
        List<OrderItemEntity> entities = e.getItems();
        if (entities != null && !entities.isEmpty()) {
            List<OrderItem> domainLines = new ArrayList<>(entities.size());
            for (OrderItemEntity ent : entities) {
                OrderItem dl = orderLineToDomain(ent);
                if (dl != null) {
                    domainLines.add(dl);
                }
            }
            o.setItems(domainLines);
        } else {
            o.setItems(new ArrayList<>());
        }

        return o;
    }

    private OrderItemEntity orderLineToEntity(OrderItem line) {
        if (line == null) return null;
        OrderItemEntity ent = new OrderItemEntity();
        ent.setProductId(line.getProductId());
        ent.setQuantity(line.getQuantity());
        ent.setUnitPrice(line.getUnitPrice());
        return ent;
    }

    private OrderItem orderLineToDomain(OrderItemEntity ent) {
        if (ent == null) return null;
        OrderItem line = new OrderItem();
        line.setProductId(ent.getProductId());
        line.setQuantity(ent.getQuantity());
        line.setUnitPrice(ent.getUnitPrice());
        return line;
    }
}
