package com.blockstock.blockstockapi.order;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public Order create(int quantity) {
        Order o = new Order();
        o.setReference(generateReference());
        o.setQuantity(quantity);
        return o;
    }

    private String generateReference() {
        SecureRandom rand = new SecureRandom();
        byte[] randomBytes = new byte[16];
        rand.nextBytes(randomBytes);
        return new String(Base64.getEncoder().encode(randomBytes));
    }
}
