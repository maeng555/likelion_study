package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Menu;
import org.example.domain.Order;
import org.example.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorageUtil {
    private static final String MENU_FILE = "src/main/resources/menu_data.json";
    private static final String USER_FILE = "src/main/resources/user_data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 📝 [메뉴 데이터 저장]
    public static void saveMenuData(List<Menu> menus) {
        try {
            objectMapper.writeValue(new File(MENU_FILE), menus);
        } catch (IOException e) {
            System.out.println("[ERROR] 메뉴 데이터를 저장할 수 없습니다.");
        }
    }

    // 📥 [메뉴 데이터 불러오기]
    public static List<Menu> loadMenuData() {
        File file = new File(MENU_FILE);
        if (!file.exists()) {
            System.out.println("[INFO] 저장된 메뉴 데이터가 없습니다.");
            return null;
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Menu>>() {});
        } catch (IOException e) {
            System.out.println("[ERROR] 메뉴 데이터를 불러올 수 없습니다.");
            return null;
        }
    }

    // 📝 [사용자 데이터 저장]
    public static void saveUserData(List<User> users) {
        try {
            // List<User>를 JSON으로 변환하여 저장
            objectMapper.writeValue(new File(USER_FILE), users);
        } catch (IOException e) {
            System.out.println("[ERROR] 사용자 데이터를 저장할 수 없습니다.");
        }
    }

    // 📥 [사용자 데이터 불러오기] 개선된 버전
    public static List<User> loadUserData() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.out.println("[INFO] 저장된 사용자 데이터가 없습니다.");
            return new ArrayList<>();  // 데이터가 없으면 빈 리스트 반환
        }

        try {
            List<User> users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            System.out.println("[INFO] 사용자 데이터를 성공적으로 불러왔습니다: " + users);  // 디버깅 출력
            return users;
        } catch (IOException e) {
            System.out.println("[ERROR] 사용자 데이터를 불러올 수 없습니다.");
            return new ArrayList<>();  // 읽기 실패 시 빈 리스트 반환
        }
    }

    // [사용자 데이터를 Map으로 변환] 개선된 버전
    public static Map<Integer, User> loadUserDataAsMap() {
        List<User> userList = loadUserData();  // List<User>로 불러오기
        Map<Integer, User> userMap = new HashMap<>();

        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                userMap.put(user.getId(), user);  // ID를 키로 하여 Map에 저장
            }
        } else {
            System.out.println("[INFO] 사용자 데이터가 없습니다.");
        }
        System.out.println("[INFO] 사용자 데이터를 Map으로 변환: " + userMap);  // 디버깅 출력
        return userMap;
    }

    // 주문 데이터를 저장할 JSON 파일 경로 추가
    private static final String ORDER_FILE = "src/main/resources/order_data.json";

    // 📝 [주문 데이터 저장]
    public static void saveOrderData(List<Order> orders) {
        try {
            objectMapper.writeValue(new File(ORDER_FILE), orders);
        } catch (IOException e) {
            System.out.println("[ERROR] 주문 데이터를 저장할 수 없습니다.");
        }
    }

    // 📥 [주문 데이터 불러오기]
    public static List<Order> loadOrderData() {
        File file = new File(ORDER_FILE);
        if (!file.exists()) {
            System.out.println("[INFO] 저장된 주문 데이터가 없습니다.");
            return new ArrayList<>(); // 빈 리스트 반환
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            System.out.println("[ERROR] 주문 데이터를 불러올 수 없습니다.");
            return new ArrayList<>();
        }
    }
}