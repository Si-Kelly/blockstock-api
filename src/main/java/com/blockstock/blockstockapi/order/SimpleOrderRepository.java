package com.blockstock.blockstockapi.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleOrderRepository implements OrderRepository {

    private final Map<String, Order> ordersMap;

    private RandomStringGenerator referenceGenerator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build();

    public SimpleOrderRepository() {
        this.ordersMap = new LinkedHashMap<String, Order>();
    }

    @Override
    public void insert(Order order) {
        order.setReference(referenceGenerator.generate(8));
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
