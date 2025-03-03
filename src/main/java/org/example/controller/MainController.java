package org.example.controller;

import org.example.entity.User;
import org.example.repository.MenuRepository;
import org.example.repository.OrderRepository;
import org.example.service.MenuService;
import org.example.service.OrderService;

import java.util.HashMap;
import java.util.Map;

public class MainController {
    private final MenuService menuService;
    //private final OrderService orderService;
    private final Map<Integer, User> users = new HashMap<>();
    private User currentUser;
    private String currentAdmin;


    public MainController() {
        this.menuService = new MenuService(new MenuRepository());

        //this.orderService = new OrderService(menuRepository);
    }

    public void printMenu() {
        System.out.println("📜 메뉴 목록을 출력합니다.");
        menuService.printMenu();
    }

    public void start() {
        while (true) {
            System.out.println("\n 키오스크 시스템");
            System.out.println("0. 종료");
            System.out.println("1. 관리자 생성");
            System.out.println("2. 관리자 접속");
            System.out.println("3. 회원 생성");
            System.out.println("4. 회원 접속");

            //예약어를 사용하면안됌
        }
    }


    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.printMenu();
    }
}