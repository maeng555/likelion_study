package org.example.controller;

import org.example.repository.MenuRepository;
import org.example.service.OrderService;
import org.example.service.MenuService;

import java.util.Scanner;

public class MainController {
    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);

    public MainController() {
        MenuRepository menuRepository = new MenuRepository();
        MenuService menuService = new MenuService(menuRepository);
        OrderService orderService = new OrderService(menuRepository);

        this.userController = new UserController(menuService, orderService);
    }

    public void start() {
        while (true) {
            System.out.println("\n0. 종료");
            System.out.println("1. 관리자 생성");
            System.out.println("2. 관리자 접속");
            System.out.println("3. 회원 생성");
            System.out.println("4. 회원 접속");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> userController.createAdmin();
                case 2 -> userController.adminLogin();
                case 3 -> userController.createUser();
                case 4 -> userController.userLogin();
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("다시 입력해주세요.");
            }
        }
    }
}