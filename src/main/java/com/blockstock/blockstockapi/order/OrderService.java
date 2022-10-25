package com.blockstock.blockstockapi.order;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
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
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build().generate(8);
    }
}
