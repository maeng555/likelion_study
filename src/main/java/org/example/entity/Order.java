package org.example.entity;

import java.util.Map;

public class Order {
    private final Map<String, Integer> items; // 상품명과 수량
    private final int totalAmount; // 총 결제 금액
    private final int userId; // 구매한 사용자 ID

    public Order(Map<String, Integer> items, int totalAmount, int userId) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getUserId() {
        return userId;
    }
}
