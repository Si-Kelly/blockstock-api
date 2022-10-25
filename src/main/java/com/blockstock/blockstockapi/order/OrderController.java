package com.blockstock.blockstockapi.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderRepository repo;

    @Autowired
    OrderService service;

    @PostMapping(path = "orders")
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {
        Order order = service.create(request.quantity);
        repo.insert(order);
        return new OrderResponse(order.getReference());
    }
}
