package org.example.repository;

import org.example.Util.MenuUtil;
import org.example.entity.Menu;

import java.util.List;

public class MenuRepository {
    private final List<Menu> menus;
    public MenuRepository() {
        this.menus = MenuUtil.loadProductsFromFile();
    }
    public List<Menu> getMenus() {
        return menus;
    } //상품 목록 리스트 getter

    public Menu findByName(String name) {
        return menus.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

}
