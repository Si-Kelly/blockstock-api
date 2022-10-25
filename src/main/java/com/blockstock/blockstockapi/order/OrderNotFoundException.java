package com.blockstock.blockstockapi.order;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Order")
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderReference) {
        super("No such order " + orderReference);
    }

}