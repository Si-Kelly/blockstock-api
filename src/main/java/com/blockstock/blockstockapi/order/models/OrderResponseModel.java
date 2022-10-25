package com.blockstock.blockstockapi.order.models;

import com.blockstock.blockstockapi.order.Order;

public class OrderResponseModel {
    public final String reference;
    public final int quantity;

    public OrderResponseModel(String reference, int quantity) {
        this.reference = reference;
        this.quantity = quantity;
    }

    public static OrderResponseModel fromOrder(Order o) {
        return new OrderResponseModel(o.getReference(), o.getQuantity());
    }

}
