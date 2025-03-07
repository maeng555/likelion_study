package org.example.controller;

import org.example.domain.User;
import org.example.service.MenuService;
import org.example.service.OrderService;

import java.util.Scanner;

public class UserController {
    private User currentAdmin;
    private User currentUser;
    private final MenuService menuService;
    private final OrderController orderController;
    private final Scanner scanner = new Scanner(System.in);

    public UserController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderController = new OrderController(orderService, menuService);
    }

    public void createAdmin() {
        System.out.println("관리자의 정보를 입력해주세요 (ex: 관리자1, 30000)");
        String input = scanner.nextLine();
        String[] adminData = input.split(",");
        int balance = Integer.parseInt(adminData[1].trim());

        currentAdmin = new User(balance, true);
        System.out.println("관리자가 생성되었습니다.");
    }

    public void adminLogin() {
        if (currentAdmin == null) {
            System.out.println("[ERROR] 관리자 생성이 필요.");
            return;
        }
        System.out.println("관리자: " + currentAdmin.getId());
    }

    public void createUser() {
        System.out.println("회원의 정보를 입력해주세요 (ex: 1, 30000)");
        String input = scanner.nextLine();
        String[] userData = input.split(",");
        int userId = Integer.parseInt(userData[0].trim());
        int balance = Integer.parseInt(userData[1].trim());

        currentUser = new User(userId, balance, false);
        System.out.println("회원이 생성되었습니다.");
    }

    public void userLogin() {
        if (currentUser == null) {
            System.out.println("[ERROR] 회원이 생성되지 않았습니다.");
            return;
        }

        System.out.println("안녕하세요 회원 " + currentUser.getId() + "님, 햄버거 가게 입니다.");
        System.out.println("현재 접속된 관리자는 관리자" + currentAdmin.getId() + "입니다");

        while (true) {
            menuService.printMenu();
            orderController.processOrder(currentUser, currentAdmin);
            System.out.println("구매하고 싶은 다른 상품이 있나요? (Y/N)");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (!choice.equals("Y")) {
                break;
            }
        }
    }
}