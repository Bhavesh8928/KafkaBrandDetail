package com.DeliveryBoy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Count {
//    private static final Logger logger = LoggerFactory.getLogger(Count.class);
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public int incrementCount() {
        return atomicInteger.incrementAndGet();
    }

    public int getFinalCount() {
        return atomicInteger.get();
    }

    public void resetCount() {
        atomicInteger.set(0);
    }

}

