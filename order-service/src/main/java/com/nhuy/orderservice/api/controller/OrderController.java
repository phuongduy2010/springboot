package com.nhuy.orderservice.api.controller;

import com.nhuy.orderservice.api.dto.CreateOrderDto;
import com.nhuy.orderservice.application.command.PlaceOrderCommand;
import com.nhuy.orderservice.application.handler.PlaceOrderHandler;
import com.nhuy.orderservice.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final PlaceOrderHandler placeOrder;
    @PostMapping("createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@RequestBody CreateOrderDto dto){
        UUID id =  placeOrder.handle(new PlaceOrderCommand(
                dto.customerId(),
                dto.items().stream().map(
                        i -> new PlaceOrderCommand.Item(i.productId(), i.quantity(), i.unitPrice())
                ).toList()
        ));
        return Map.of("orderId",id);
    }

    @GetMapping("getAllOrders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllProducts(){
        return  placeOrder.getOrders();
    }

    @GetMapping("getOrder/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable UUID orderId){
        return  placeOrder.getOrderById(orderId);
    }
}
