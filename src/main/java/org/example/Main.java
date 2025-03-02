package org.example;

import org.example.controller.MainController;
import org.example.repository.MenuRepository;
import org.example.service.MenuService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        MainController mainController = new MainController();
        mainController.printMenu();

    }
}