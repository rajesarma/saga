package com.sarma.saga.lib.model;

public enum PaymentMethod {
    CASH(1),
    UPI(2),
    NET_BANKING(3);

    private final int value;

    public int getValue() {
        return value;
    }

    PaymentMethod(int value) {
        this.value = value;
    }

}
