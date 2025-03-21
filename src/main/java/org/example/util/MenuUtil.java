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
    private static final String MENU_FILE = "src/main/resources/products.md"; // 원본 Markdown 데이터
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 라이브러리 사용

    // ✅ [1] JSON 또는 Markdown에서 메뉴 데이터를 불러오기
    public static List<Menu> loadProductsFromFile() {
        List<Menu> menus = DataStorageUtil.loadMenuData();
        if (menus == null || menus.isEmpty()) {
            return loadFromMarkdownFile(); // JSON에 데이터가 없으면 Markdown에서 불러오기
        }
        return menus;
    }

    // ✅ [2] Markdown 파일에서 데이터를 불러오는 메서드
    private static List<Menu> loadFromMarkdownFile() {
        List<Menu> menus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue; // 데이터 형식이 맞지 않으면 무시

                menus.add(new Menu(
                        data[0],  // 제품명
                        Integer.parseInt(data[1]), // 가격
                        Integer.parseInt(data[2]), // 수량
                        data[3],  // 카테고리
                        data[4]   // 설명
                ));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 메뉴 데이터를 불러올 수 없습니다.");
        }

        // ✅ JSON 파일에도 저장
        saveProductsToFile(menus);
        return menus;
    }

    // ✅ [3] 메뉴 데이터를 JSON 파일로 저장
    public static void saveProductsToFile(List<Menu> menus) {
        DataStorageUtil.saveMenuData(menus);
    }
}