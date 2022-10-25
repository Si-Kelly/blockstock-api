package com.blockstock.blockstockapi.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (ordersMap.containsKey(order.getReference())) {
            throw new DuplicateOrderReferenceException();
        }
        ordersMap.put(order.getReference(), order);
    }

    @Override
    public Order getByReference(String orderReference) {
        Order order = this.ordersMap.get(orderReference);
        return order;
    }

    @Override
    public Order update(Order orderUpdate) {
        return ordersMap.put(orderUpdate.getReference(), orderUpdate);
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<Order>(ordersMap.values());
    }

}
