package org.example.service;

import org.example.entity.Menu;
import org.example.entity.Order;
import org.example.repository.MenuRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final MenuRepository menuRepository;

    public OrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
    public Order createOrder(int userId, Map<String, Integer> orderItems) throws IllegalAccessException {
        Map<String, Integer> validOrder = new HashMap<>();
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Menu menu = menuRepository.findByName(productName);
            if (menu == null) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
            }

            // 재고 확인 후 주문 가능하면 수량 차감
            menu.decreaseQuantity(quantity);
            validOrder.put(productName, quantity);
            totalAmount += menu.getPrice() * quantity;
        }

        return new Order(validOrder, totalAmount, userId);
    }



}
