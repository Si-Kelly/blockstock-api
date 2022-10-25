package com.blockstock.blockstockapi.order;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SimpleOrderRepository implements OrderRepository {

    private final Map<String, Order> ordersMap;

    public SimpleOrderRepository() {
        this.ordersMap = new LinkedHashMap<String, Order>();
    }

    @Override
    public void insert(Order order) {
        ordersMap.put(order.getReference(), order);
    }

}
