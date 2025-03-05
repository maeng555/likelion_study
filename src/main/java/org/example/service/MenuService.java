package org.example.service;

import org.example.repository.MenuRepository;

public class MenuService {
    private final MenuRepository menuRepository;
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
    public void printMenu() {
        menuRepository.getMenus().forEach(System.out::println);
    }
    public int getPriceByName(String productName) {
        return menuRepository.getPriceByName(productName);
    }


}
