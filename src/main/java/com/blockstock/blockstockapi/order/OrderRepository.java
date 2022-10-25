package com.blockstock.blockstockapi.order;

public interface OrderRepository {

    void insert(Order order);

    Order getByReference(String orderReference);

    Order update(Order orderUpdate);

}
