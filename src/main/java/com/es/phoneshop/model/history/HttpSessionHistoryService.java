package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public class HttpSessionHistoryService implements HistoryService {

    private static final String HISTORY_KEY = "history";

    private HttpSessionHistoryService() {
    }

    private static class SingletonHandler {
        static final HttpSessionHistoryService instance = new HttpSessionHistoryService();
    }

    public static HttpSessionHistoryService newInstance() {
        return HttpSessionHistoryService.SingletonHandler.instance;
    }

    @Override
    public History getHistoryFromSource(Object src) {
        History history;
        HttpSession session;
        try {
            session = (HttpSession) src;
            history = (History) session.getAttribute(HISTORY_KEY);
            if (history == null) {
                history = new History();
                session.setAttribute(HISTORY_KEY, history);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Source must be HttpSession");
        }
        return history;
    }

    @Override
    public void put(History history, Product product) {
        int size = history.getRecentProducts().size();
        if (history.getRecentProducts().stream()
                .anyMatch(product1 -> product1.equals(product))) {
            history.getRecentProducts().remove(product);
        } else if (size > 2) {
            history.getRecentProducts().removeLast();
        }
        history.getRecentProducts().addFirst(product);
    }

}
