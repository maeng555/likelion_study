package org.example.domain;

import java.util.Map;

public class Order {
    private final Map<String, Integer> items;
    private final int totalAmount;
    private final int userId;

    public Order(Map<String, Integer> items, int totalAmount, int userId) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

}
