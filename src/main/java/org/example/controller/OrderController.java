package org.example.controller;

import org.example.entity.Order;
import org.example.service.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void processOrder(int userId) {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [치킨버거-3],[콜라-2])");
        String input = scanner.nextLine();

        // 입력값을 파싱하여 Map에 저장
        Map<String, Integer> orderItems = parseOrderInput(input);

        if (orderItems.isEmpty()) {
            System.out.println("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
            return;
        }

        try {
            Order order = orderService.createOrder(userId, orderItems);
            System.out.println("✅ 주문이 완료되었습니다! " + order);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private Map<String, Integer> parseOrderInput(String input) {
        Map<String, Integer> orderItems = new HashMap<>();

        try {
            input = input.replaceAll("[\\[\\]]", ""); // 대괄호 제거
            String[] items = input.split(",");

            for (String item : items) {
                String[] parts = item.split("-");
                if (parts.length != 2) throw new IllegalArgumentException();

                String productName = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                if (quantity <= 0) throw new IllegalArgumentException();

                orderItems.put(productName, quantity);
            }
        } catch (Exception e) {
            return new HashMap<>(); // 잘못된 입력일 경우 빈 Map 반환
        }

        return orderItems;
    }
}
