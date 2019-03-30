package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

public interface HistoryService {

    History getHistoryFromSource(Object src);

    void put(History history, Product product);
}
