package org.example.service;

import org.example.domain.Menu;
import org.example.domain.Order;
import org.example.domain.User;
import org.example.repository.MenuRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private final MenuRepository menuRepository;

    public OrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Order createOrder(Map<String, Integer> orderItems, User user, User admin) {
        Map<String, Integer> validOrder = new HashMap<>();
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Menu menu = menuRepository.findByName(productName);

            menu.decreaseQuantity(quantity);
            validOrder.put(productName, quantity);
            totalAmount += menu.getPrice() * quantity;
        }

        user.pay(totalAmount);
        admin.pay(totalAmount);

        return new Order(validOrder, totalAmount, user.getId());
    }
}
