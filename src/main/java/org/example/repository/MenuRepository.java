package org.example.repository;

import org.example.util.MenuUtil;
import org.example.domain.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuRepository {
    private final List<Menu> menus;
    private final Map<String, Integer> drinkStock;

    public MenuRepository() {
        this.menus = MenuUtil.loadProductsFromFile();
        this.drinkStock = new HashMap<>();


        for (Menu menu : menus) {
            if ("음료수".equals(menu.getCategory())) {
                drinkStock.put(menu.getName(), menu.getStock());
            }
        }
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Menu findByName(String name) {
        return menus.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }

    public int getPriceByName(String name) {
        Menu menu = findByName(name);
        return menu.getPrice();
    }

    public boolean isDrinkAvailable(String drinkName, int quantity) {
        Integer stock = drinkStock.get(drinkName);
        return stock != null && stock >= quantity;
    }

    public void reduceDrinkStock(String drinkName, int quantity) {
        if (isDrinkAvailable(drinkName, quantity)) {
            drinkStock.put(drinkName, drinkStock.get(drinkName) - quantity);

            try {
                Menu drink = findByName(drinkName);
                drink.decreaseQuantity(quantity);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + drinkName + " 재고 차감 중 오류 발생.");
            }
        } else {
            System.out.println("[ERROR] " + drinkName + "의 재고가 부족합니다.");
        }
    }
}