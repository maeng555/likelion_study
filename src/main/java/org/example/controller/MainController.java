package org.example.controller;

import org.example.repository.MenuRepository;
import org.example.service.MenuService;

public class MainController {
    private final MenuService menuService;


    public MainController() {
        this.menuService = new MenuService(new MenuRepository());
    }

    public void printMenu() {
        System.out.println("📜 메뉴 목록을 출력합니다.");
        menuService.printMenu();
    }


    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.printMenu();
    }
}