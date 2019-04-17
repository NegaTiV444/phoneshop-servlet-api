package com.es.phoneshop.model.enums;

public enum PaymentMethod {

    CASH("Cash"),
    CREDIT_CARD("Credit card");

    private String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
