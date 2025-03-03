package org.example.service;

import org.example.entity.Menu;
import org.example.repository.MenuRepository;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final MenuRepository menuRepository;
    private final List<Menu> cart = new ArrayList<>();

    public OrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public void addToCart(String name, int quantity) throws IllegalAccessException {
        Menu menu = menuRepository.findByName(name);
        if (menu == null || menu.isSoldOut()) {
            System.out.println("[ERROR] " + name + "은 존재하지 않거나 품절된 상품입니다.");
            return;
        }

        if (menu.getQuantity() < quantity) {
            System.out.println("[ERROR] " + name + "의 재고가 부족합니다.");
            return;
        }

        menu.decreaseQuantity(quantity);
        cart.add(new Menu(name, menu.getPrice(), quantity, menu.getDescription(), menu.getCategory()));


        if (name.contains("세트")) {
            decreaseSetItems(quantity);
        }

    }


    private void decreaseSetItems(int quantity) throws IllegalAccessException {
        Menu fries = menuRepository.findByName("감자튀김");
        Menu drink = menuRepository.findByName("콜라");

        if (fries != null && fries.getQuantity() >= quantity) {
            fries.decreaseQuantity(quantity);
        } else {
            System.out.println("[ERROR] 감자튀김 재고 부족! 세트 구매 불가.");
        }
        if (drink != null && drink.getQuantity() >= quantity) {
            drink.decreaseQuantity(quantity);
        } else {
            System.out.println("[ERROR] 콜라 재고 부족! 세트 구매 불가.");
        }
    }


    public void printCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 장바구니가 비어 있습니다.");
            return;
        }

        System.out.println("\n🛒 장바구니 목록:");
        int totalAmount = 0;
        for (Menu menu : cart) {
            System.out.println(menu.getName() + " | " + menu.getQuantity() + "개 | " + (menu.getPrice() * menu.getQuantity()) + "원");
            totalAmount += menu.getPrice() * menu.getQuantity();
        }
        System.out.println("총 금액: " + totalAmount + "원");
    }

    public List<Menu> getCart() {
        return cart;
    }


    public void clearCart() {
        cart.clear();
    }
}
