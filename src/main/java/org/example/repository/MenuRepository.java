package org.example.repository;

import org.example.util.DataStorageUtil;
import org.example.util.MenuUtil;
import org.example.domain.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuRepository {
    private List<Menu> menus;
    private final Map<String, Integer> drinkStock;

    public MenuRepository() {
        List<Menu> loadedMenus = DataStorageUtil.loadMenuData(); // 🔹 JSON에서 메뉴 불러오기
        this.drinkStock = new HashMap<>();

        if (loadedMenus == null || loadedMenus.isEmpty()) {
            System.out.println("[INFO] 기존 메뉴 데이터가 없어 기본 메뉴를 로드합니다.");
            loadedMenus = MenuUtil.loadProductsFromFile(); // 🔹 Markdown 파일에서 불러오기
            DataStorageUtil.saveMenuData(loadedMenus); // 🔹 JSON에 저장
        }

        this.menus = loadedMenus;

        // 🔹 음료수 재고 초기화
        for (Menu menu : menus) {
            if ("음료수".equals(menu.getCategory())) {
                drinkStock.put(menu.getName(), menu.getStock());
            }
        }
    }

    // ✅ 메뉴 리스트 조회
    public List<Menu> getMenus() {
        return menus;
    }

    // ✅ 특정 메뉴 찾기
    public Menu findByName(String name) {
        return menus.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }

    public int getPriceByName(String name) {
        Menu menu = findByName(name);  // 이름으로 메뉴 찾기
        return menu.getPrice();        // 가격 반환
    }

    // ✅ 음료 재고 확인
    public boolean isDrinkAvailable(String drinkName, int quantity) {
        Integer stock = drinkStock.get(drinkName);
        return stock != null && stock >= quantity;
    }

    // ✅ 음료 재고 차감
    public void reduceDrinkStock(String drinkName, int quantity) {
        if (isDrinkAvailable(drinkName, quantity)) {
            drinkStock.put(drinkName, drinkStock.get(drinkName) - quantity);

            try {
                Menu drink = findByName(drinkName);
                drink.decreaseQuantity(quantity);
                updateMenuData(); // 🔹 변경된 데이터 저장
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + drinkName + " 재고 차감 중 오류 발생.");
            }
        } else {
            System.out.println("[ERROR] " + drinkName + "의 재고가 부족합니다.");
        }
    }

    // ✅ [데이터 저장] 변경된 메뉴 데이터를 JSON에 반영
    public void updateMenuData() {
        DataStorageUtil.saveMenuData(menus); // 🔹 메뉴 데이터를 JSON 파일에 저장
    }
}