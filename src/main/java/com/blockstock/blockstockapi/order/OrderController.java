package com.blockstock.blockstockapi.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blockstock.blockstockapi.order.models.CreateOrderRequest;
import com.blockstock.blockstockapi.order.models.CreateOrderResponse;
import com.blockstock.blockstockapi.order.models.OrderListResponse;
import com.blockstock.blockstockapi.order.models.OrderResponseModel;
import com.blockstock.blockstockapi.order.models.UpdateOrderRequest;

@RestController
public class OrderController {

    @Autowired
    OrderRepository repo;

    @Autowired
    OrderService service;

    @PostMapping(path = "orders")
    public CreateOrderResponse placeOrder(@RequestBody CreateOrderRequest request) {
        Order order = service.create(request.quantity);
        repo.insert(order);
        return new CreateOrderResponse(order.getReference());
    }

    @GetMapping(path = "orders/")
    public OrderListResponse listOrders() {
        List<Order> allOrders = repo.getAll();
        return new OrderListResponse(allOrders);
    }

    @GetMapping(path = "orders/{orderReference}")
    public OrderResponseModel fetchOrder(
            @PathVariable(name = "orderReference", required = true) String orderReference) {

        Order order = repo.getByReference(orderReference);
        if (order == null) {
            throw new OrderNotFoundException(orderReference);
        }
        return OrderResponseModel.fromOrder(order);
    }

    @PatchMapping(path = "orders/{orderReference}")
    public OrderResponseModel updateOrder(
            @PathVariable(name = "orderReference", required = true) String orderReference,
            @RequestBody UpdateOrderRequest request) {

        Order order = repo.getByReference(orderReference);
        if (order == null) {
            throw new OrderNotFoundException(orderReference);
        }
        order.setQuantity(request.quantity);
        repo.update(order);
        return OrderResponseModel.fromOrder(order);
    }

}
