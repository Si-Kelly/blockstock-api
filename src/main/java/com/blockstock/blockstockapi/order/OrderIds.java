package com.blockstock.blockstockapi.order;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderIds {
    private static AtomicInteger counter;

    public static int next() {
        return counter.incrementAndGet();
    };
}
