package org.example.controller;

import org.example.domain.User;
import org.example.domain.Order;
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
                System.out.println("다시입력");
            }
        }
    }

    private void createAdmin() {
        System.out.println("관리자의 정보를 입력해주세요 (ex: 관리자1, 30000)");
        String input = scanner.nextLine();
        String[] adminData = input.split(",");
        //String adminName = adminData[0].trim();
        int balance = Integer.parseInt(adminData[1].trim());

        currentAdmin = new User(balance, true);
        System.out.println("관리자가 생성");
    }

    private void adminLogin() {
        if (currentAdmin == null) {
            System.out.println("[ERROR] 관리자 생성이 필요.");
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

        currentUser = new User(userId, balance, false);
        System.out.println("회원 생성");
    }

    private void userLogin() {
        if (currentUser == null) {
            System.out.println("[ERROR] 회원이 생성되지 않았습니다.");
            return;
        }

        System.out.println("안녕하세요 회원 " + currentUser.getId() + "님, 햄버거 가게 입니다.");
        System.out.println("현재 접속된 관리자는 관리자" + currentAdmin.getId() + "입니다");

        while (true) {
            menuService.printMenu();
            processOrder();
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

            Order order = orderService.createOrder(orderItems, currentUser, currentAdmin);

            printOrderSummary(order);

        } catch (IllegalAccessException e) {
            System.out.println("[ERROR] 주문 처리 중 오류 발생.");
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
                throw new IllegalArgumentException("[ERROR] 올바른 형식이 아닙니다." + item);
            }
            String productName = itemData[0].trim();
            int quantity = Integer.parseInt(itemData[1].trim());
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
            int price = menuService.getPriceByName(productName);
            System.out.println(productName + " " + quantity + " " + (quantity * price));
            totalAmount += (quantity * price);
        }

        System.out.println("=====================");
        System.out.println("총구매액 " + totalAmount);
        System.out.println("=====================");
        System.out.println("판매자: 관리자 " + currentAdmin.getId() + ", " + currentAdmin.getBalance());
        System.out.println("구매자: " + currentUser.getId() + ", " + currentUser.getBalance());
    }
}