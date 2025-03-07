package org.example.repository;

import org.example.util.MenuUtil;
import org.example.domain.Menu;

import java.util.List;

public class MenuRepository {
    private final List<Menu> menus;

    public MenuRepository() {
        this.menus = MenuUtil.loadProductsFromFile();
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
        if (menu != null) {
            return menu.getPrice();
        }
        return 0;
    }

}
