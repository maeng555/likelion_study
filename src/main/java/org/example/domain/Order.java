package org.example.domain;

import java.util.Map;

public class Order {
    private final Map<String, Integer> items;

    public Order(Map<String, Integer> items, int totalAmount, int userId) {
        this.items = items;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

}
