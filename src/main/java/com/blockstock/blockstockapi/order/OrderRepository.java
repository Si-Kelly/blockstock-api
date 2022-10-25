package com.blockstock.blockstockapi.order;

import java.util.List;

public interface OrderRepository {

    void insert(Order order);

    Order getByReference(String orderReference);

    Order update(Order orderUpdate);

    List<Order> getAll();

}
