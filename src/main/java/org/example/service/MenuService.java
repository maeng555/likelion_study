package org.example.service;

import org.example.domain.Menu;
import org.example.repository.MenuRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuService {
    private final MenuRepository menuRepository;
    private final Scanner scanner = new Scanner(System.in);

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void printMenu() {
        menuRepository.getMenus().forEach(System.out::println);
    }

    public int getPriceByName(String productName) {
        return menuRepository.getPriceByName(productName);
    }


    public void handleSetMenuItems(String productName, int quantity) {
        if (productName.contains("세트")) {

            String burgerName = productName.replace("세트", "").trim();
            try {
                Menu burger = menuRepository.findByName(burgerName);
                burger.decreaseQuantity(quantity);
                System.out.println(burgerName + " " + quantity + "개가 차감되었습니다.");
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + burgerName + " 재고가 부족합니다.");
                return;
            }

            if (!reduceSideStock(quantity)) return;

            askForDrinkQuantity(quantity);
        }
    }


    private boolean reduceSideStock(int quantity) {
        try {
            Menu fries = menuRepository.findByName("감자튀김");
            fries.decreaseQuantity(quantity);
            System.out.println("감자튀김 " + quantity + "개가 차감되었습니다.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 감자튀김 재고가 부족합니다.");
            return false;
        }
    }


    private void askForDrinkQuantity(int quantity) {
        Map<String, Integer> drinkChoices = new HashMap<>();
        int totalDrinks = 0;

        while (totalDrinks < quantity) {
            System.out.println("음료수를 선택해 주세요. (콜라, 제로콜라)");
            String drinkChoice = scanner.nextLine().trim();

            System.out.println(drinkChoice + " 몇 개를 원하시나요?");
            int drinkCount = Integer.parseInt(scanner.nextLine().trim());

            if (totalDrinks + drinkCount > quantity) {
                System.out.println("[ERROR] 총 " + quantity + "개를 넘길 수 없습니다. 다시 입력하세요.");
                continue;
            }

            if (!menuRepository.isDrinkAvailable(drinkChoice, drinkCount)) {
                System.out.println("[ERROR] " + drinkChoice + "의 재고가 부족합니다. 다시 선택해주세요.");
                continue;
            }

            drinkChoices.put(drinkChoice, drinkChoices.getOrDefault(drinkChoice, 0) + drinkCount);
            totalDrinks += drinkCount;
        }

        for (Map.Entry<String, Integer> entry : drinkChoices.entrySet()) {
            menuRepository.reduceDrinkStock(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + " " + entry.getValue() + "개가 차감되었습니다.");
        }
    }
}