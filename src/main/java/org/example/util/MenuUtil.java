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
    private static final String MENU_FILE = "src/main/resources/products.md"; // 원래 불러오던 파일
    private static final String MENU_JSON_FILE = "menu_data.json"; // 저장할 JSON 파일
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 사용

    // [1] JSON 파일에서 메뉴 데이터를 불러오는 메서드
    public static List<Menu> loadProductsFromFile() {
        File file = new File(MENU_JSON_FILE);

        // JSON 파일이 존재하면 불러오기
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Menu>>() {});
            } catch (IOException e) {
                System.out.println("[ERROR] 저장된 메뉴 데이터를 불러올 수 없습니다.");
            }
        }

        // JSON 파일이 없으면 기존 MD 파일에서 불러오기
        return loadFromMarkdownFile();
    }

    // [2] Markdown 파일에서 데이터를 불러오는 메서드 (초기 데이터)
    private static List<Menu> loadFromMarkdownFile() {
        List<Menu> menus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                menus.add(new Menu(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], data[4]));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일을 읽을 수 없습니다.");
        }

        // 처음 불러온 데이터를 JSON 파일로 저장
        saveProductsToFile(menus);
        return menus;
    }

    // [3] 프로그램 종료 시 메뉴 데이터를 JSON 파일에 저장하는 메서드
    public static void saveProductsToFile(List<Menu> menus) {
        try {
            objectMapper.writeValue(new File(MENU_JSON_FILE), menus);
        } catch (IOException e) {
            System.out.println("[ERROR] 메뉴 데이터를 저장할 수 없습니다.");
        }
    }
}