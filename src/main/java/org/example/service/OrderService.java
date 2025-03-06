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

    public Order createOrder(Map<String, Integer> orderItems, User user, User admin) throws IllegalAccessException {
        Map<String, Integer> validOrder = new HashMap<>();
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Menu menu = menuRepository.findByName(productName);
            if (menu == null) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
            }

            menu.decreaseQuantity(quantity);
            validOrder.put(productName, quantity);
            totalAmount += menu.getPrice() * quantity;
        }

        user.pay(totalAmount); // 구매자 잔액 차감
        admin.pay(totalAmount); // 관리자 잔액 추가

        return new Order(validOrder, totalAmount, user.getId());
    }
}
