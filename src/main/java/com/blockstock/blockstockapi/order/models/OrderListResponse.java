package com.blockstock.blockstockapi.order.models;

import java.util.List;
import java.util.stream.Collectors;

import com.blockstock.blockstockapi.order.Order;

public class OrderListResponse {
    public final OrderResponseModel[] orders;

    public OrderListResponse(List<Order> orders2) {
        this.orders = orders2.stream()
                .map(o -> OrderResponseModel.fromOrder(o))
                .collect(Collectors.toList())
                .toArray(new OrderResponseModel[orders2.size()]);
    }
}
