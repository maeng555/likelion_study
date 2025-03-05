package org.example.controller;

import org.example.entity.User;
import org.example.entity.Order;
import org.example.repository.MenuRepository;
import org.example.service.OrderService;
import org.example.service.MenuService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainController {
    private final MenuService menuService;
    private final OrderService orderService;

    private final Scanner scanner = new Scanner(System.in);

    private User currentAdmin;
    private User currentUser;

    public MainController() {
        MenuRepository menuRepository = new MenuRepository();
        this.menuService = new MenuService(menuRepository);
        this.orderService = new OrderService(menuRepository);
    }

    public void start() {
        while (true) {
            System.out.println("\n0. 종료");
            System.out.println("1. 관리자 생성");
            System.out.println("2. 관리자 접속");
            System.out.println("3. 회원 생성");
            System.out.println("4. 회원 접속");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 제거

            if (choice == 1) {
                createAdmin();
            } else if (choice == 2) {
                adminLogin();
            } else if (choice == 3) {
                createUser();
            } else if (choice == 4) {
                userLogin();
            } else if (choice == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("[ERROR] 올바르지 않은 선택입니다.");
            }
        }
    }

    private void createAdmin() {
        System.out.println("관리자의 정보를 입력해주세요 (ex: 관리자1, 30000)");
        String input = scanner.nextLine();
        String[] adminData = input.split(",");
        String adminName = adminData[0].trim();
        int balance = Integer.parseInt(adminData[1].trim());

        currentAdmin = new User(1, balance, true); // 관리자 생성 (ID는 고정으로 1)
        System.out.println("관리자가 생성되었습니다.");
    }

    private void adminLogin() {
        if (currentAdmin == null) {
            System.out.println("[ERROR] 관리자 생성이 필요합니다.");
            return;
        }

        System.out.println("관리자: " + currentAdmin.getId());
    }

    private void createUser() {
        System.out.println("회원의 정보를 입력해주세요 (ex: 1, 30000)");
        String input = scanner.nextLine();
        String[] userData = input.split(",");
        int userId = Integer.parseInt(userData[0].trim());
        int balance = Integer.parseInt(userData[1].trim());

        currentUser = new User(userId, balance, false); // 회원 생성
        System.out.println("회원이 생성되었습니다.");
    }

    private void userLogin() {
        if (currentUser == null) {
            System.out.println("[ERROR] 회원이 생성되지 않았습니다.");
            return;
        }

        System.out.println("회원 " + currentUser.getId() + "님, 접속 성공!");

        while (true) {
            menuService.printMenu();
            processOrder(); // 주문 처리
            System.out.println("구매하고 싶은 다른 상품이 있나요? (Y/N)");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (!choice.equals("Y")) {
                break;
            }
        }
    }

    private void processOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [치킨버거-3],[불고기버거세트-2])");
        String input = scanner.nextLine();

        Map<String, Integer> orderItems = parseOrderItems(input);

        try {
            // 주문 생성 후 관리자에게 금액을 추가하고, 잔액을 차감
            Order order = orderService.createOrder(orderItems, currentUser, currentAdmin);

            printOrderSummary(order);

        } catch (IllegalAccessException e) {
            System.out.println("[ERROR] 주문 처리 중 오류 발생.");
        }
    }

    private Map<String, Integer> parseOrderItems(String input) {
        // 입력값을 파싱하여 Map 형태로 변환
        Map<String, Integer> orderItems = new HashMap<>();
        String[] items = input.split("\\],\\["); // 각 항목을 [로 구분 (], [로 나누기)

        // 앞뒤 괄호 제거
        if (items[0].startsWith("[")) {
            items[0] = items[0].substring(1);
        }
        if (items[items.length - 1].endsWith("]")) {
            items[items.length - 1] = items[items.length - 1].substring(0, items[items.length - 1].length() - 1);
        }

        // 각 항목 파싱
        for (String item : items) {
            String[] itemData = item.split("-"); // 상품명과 수량 구분
            String productName = itemData[0].trim();
            int quantity = Integer.parseInt(itemData[1].trim()); // 수량 파싱
            orderItems.put(productName, quantity);
        }
        return orderItems;
    }

    private void printOrderSummary(Order order) {
        System.out.println("=====================");
        System.out.println("상품명   수량   금액");
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            int price = menuService.getPriceByName(productName); // 가격 조회 수정
            System.out.println(productName + " " + quantity + " " + (quantity * price));
            totalAmount += (quantity * price);
        }

        System.out.println("=====================");
        System.out.println("총구매액 " + totalAmount);
        System.out.println("=====================");
        System.out.println("판매자: 관리자 " +  currentAdmin.getId() + ", " + currentAdmin.getBalance());
        System.out.println("구매자: " + currentUser.getId() + ", " + currentUser.getBalance());
    }
}