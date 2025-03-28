package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    private static final String MENU_FILE = "src/main/resources/products.md";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static List<Menu> loadProductsFromFile() {
        List<Menu> menus = DataStorageUtil.loadMenuData();
        if (menus == null || menus.isEmpty()) {
            return loadFromMarkdownFile();
        }
        return menus;
    }


    private static List<Menu> loadFromMarkdownFile() {
        List<Menu> menus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;

                menus.add(new Menu(
                        data[0],
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        data[3],
                        data[4]
                ));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 메뉴 데이터를 불러올 수 없습니다.");
        }


        saveProductsToFile(menus);
        return menus;
    }


    public static void saveProductsToFile(List<Menu> menus) {
        DataStorageUtil.saveMenuData(menus);
    }
}