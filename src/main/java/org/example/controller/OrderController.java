package org.example.controller;

import org.example.domain.Order;
import org.example.domain.User;
import org.example.service.MenuService;
import org.example.service.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final MenuService menuService;
    private final Scanner scanner = new Scanner(System.in);

    public OrderController(OrderService orderService, MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
    }

    public void processOrder(User currentUser, User currentAdmin) {
        try {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [치킨버거-3],[불고기버거세트-2])");
            String input = scanner.nextLine();

            Map<String, Integer> orderItems = parseOrderItems(input);


            for (String productName : orderItems.keySet()) {
                menuService.handleSetMenuItems(productName, orderItems.get(productName));
            }

            Order order = orderService.createOrder(orderItems, currentUser, currentAdmin);
            printOrderSummary(order, currentUser, currentAdmin);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("다시 시도해 주세요.");
        }
    }

    private Map<String, Integer> parseOrderItems(String input) {
        Map<String, Integer> orderItems = new HashMap<>();
        String[] items = input.split("\\],\\[");

        int firstItem = 0;
        int lastItem = items.length - 1;
        if (items[firstItem].startsWith("[")) {
            items[firstItem] = items[firstItem].substring(1);
        }
        if (items[lastItem].endsWith("]")) {
            items[lastItem] = items[lastItem].substring(0, items[lastItem].length() - 1);
        }

        for (String item : items) {
            String[] itemData = item.split("-");
            if (itemData.length != 2) {
                throw new IllegalArgumentException("[ERROR] 올바른 형식이 아닙니다: " + item);
            }
            String productName = itemData[0].trim();
            int quantity = Integer.parseInt(itemData[1].trim());
            orderItems.put(productName, quantity);
        }
        return orderItems;
    }

    private void printOrderSummary(Order order, User currentUser, User currentAdmin) {
        System.out.println("=====================");
        System.out.println("상품명   수량   금액");
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            int price = menuService.getPriceByName(productName);
            System.out.printf("%-10s %2d개 %6d원%n", productName, quantity, quantity * price);
            totalAmount += quantity * price;
        }

        System.out.println("=====================");
        System.out.printf("총구매액: %d원%n", totalAmount);
        System.out.println("=====================");
        System.out.printf("판매자: 관리자 %d, 잔액: %d원%n", currentAdmin.getId(), currentAdmin.getBalance());
        System.out.printf("구매자: 회원 %d, 잔액: %d원%n", currentUser.getId(), currentUser.getBalance());
    }
}