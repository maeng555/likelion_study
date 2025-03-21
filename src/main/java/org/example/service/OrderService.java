package org.example.service;

import org.example.domain.Menu;
import org.example.domain.Order;
import org.example.domain.User;
import org.example.repository.MenuRepository;
import org.example.util.DataStorageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final MenuRepository menuRepository;
    private List<Order> orderHistory;

    public OrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
        this.orderHistory = DataStorageUtil.loadOrderData();
        // 🔹 JSON에서 주문 내역 불러오기
        if (this.orderHistory == null) {
            this.orderHistory = new ArrayList<>();
        }

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

        Order newOrder = new Order(validOrder, totalAmount, user.getId());
        orderHistory.add(newOrder);
        saveOrderData(); // 🔹 주문 내역 저장
        menuRepository.updateMenuData(); // 🔹 메뉴 데이터 저장
        return newOrder;
    }
    private void saveOrderData() {
        DataStorageUtil.saveOrderData(orderHistory); // 🔹 주문 내역을 JSON에 저장
    }
}
