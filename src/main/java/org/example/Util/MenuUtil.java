package org.example.Util;

import org.example.entity.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    private static final String MENU_FILE = "src/main/resources/products.md";

    public static List<Menu> loadProductsFromFile() {
        List<Menu> menus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                menus.add(new Menu(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3],data[4]));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일을 읽을 수 없습니다.");
        }
        return menus;
    }
}
