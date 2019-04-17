package com.es.phoneshop.model.enums;

public enum DeliveryMethod {

    COURIER("Courier"),
    STOREPICKUP("Store pickup");

    private String label;

    public String getLabel() {
        return label;
    }

    DeliveryMethod(String label) {
        this.label = label;
    }
}
