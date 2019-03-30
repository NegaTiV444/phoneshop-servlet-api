package com.es.phoneshop.model.exceptions;

import javax.servlet.ServletException;

public class ProductNotFoundException extends ServletException {

    public ProductNotFoundException(String msg){
        super(msg);
    }
}
