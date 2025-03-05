package org.example.controller;

import org.example.entity.Order;
import org.example.entity.User;
import org.example.repository.MenuRepository;
import org.example.service.OrderService;

import java.util.Map;

public class OrderController {
    private final OrderService orderService;
    private final MenuRepository menuRepository;

    public OrderController(OrderService orderService,MenuRepository menuRepository) {
        this.orderService = orderService;
        this.menuRepository = menuRepository;
    }


    public Order createOrder(User user, Map<String, Integer> orderItems) throws IllegalAccessException {
        return orderService.createOrder(orderItems,user,user);
    }


    public void printOrderSummary(Order order) {
        System.out.println("=====================");
        System.out.println("상품명   수량   금액");
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(productName + " " + quantity + " " + (quantity * getMenuPrice(productName)));
            totalAmount += (quantity * getMenuPrice(productName));
        }

        System.out.println("=====================");
        System.out.println("총구매액 " + totalAmount);
        System.out.println("=====================");
    }
    private int getMenuPrice(String productName) {
        return menuRepository.getPriceByName(productName);
    }
}