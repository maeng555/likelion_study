package org.example.controller;

import org.example.repository.MenuRepository;
import org.example.service.MenuService;
import org.example.service.OrderService;

import java.util.Scanner;

public class MainController {
    private final MenuService menuService;
    private final OrderController orderController;
    private final Scanner scanner = new Scanner(System.in);

    public MainController() {
        MenuRepository menuRepository = new MenuRepository();
        this.menuService = new MenuService(menuRepository);
        OrderService orderService = new OrderService(menuRepository);
        this.orderController = new OrderController(orderService);
    }

    public void start() {
        while (true) {
            System.out.println("\n 키오스크 시스템");
            System.out.println("1. 메뉴 출력");
            System.out.println("2. 주문하기");
            System.out.println("0. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 제거

            if (choice == 1) {
                menuService.printMenu();
            } else if (choice == 2) {
                System.out.println("회원 ID를 입력하세요:");
                int userId = scanner.nextInt();
                scanner.nextLine();
                orderController.processOrder(userId);
            } else if (choice == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("[ERROR] 올바르지 않은 선택입니다.");
            }
        }
    }


}