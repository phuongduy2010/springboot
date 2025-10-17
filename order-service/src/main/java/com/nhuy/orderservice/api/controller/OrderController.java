package com.nhuy.orderservice.api.controller;

import com.nhuy.orderservice.api.dto.CreateOrderDto;
import com.nhuy.orderservice.application.command.PlaceOrderCommand;
import com.nhuy.orderservice.application.handler.PlaceOrderHandler;
import com.nhuy.orderservice.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Order>> getAllOrders(@PageableDefault(page = 0,size=10, sort = "createdAt", direction = Sort.Direction.DESC)
                                                        Pageable pageable){
        return  new ResponseEntity<>(placeOrder.getOrders(pageable), HttpStatus.OK);
    }

    @GetMapping("getOrder/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable UUID orderId){
        return  placeOrder.getOrderById(orderId);
    }

    @GetMapping("getOrdersByCustomerId")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Order>> getOrdersByCustomerId(
            @RequestParam(required = false) UUID customerId,
            @PageableDefault(page = 0,size=10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        List<Order> list;
        if(customerId != null){
            list = placeOrder.getOrdersByCustomerId(customerId, pageable);

        }
        else {
            list = placeOrder.getOrders(pageable);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
