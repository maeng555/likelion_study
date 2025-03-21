package org.example.controller;

import org.example.domain.User;
import org.example.service.MenuService;
import org.example.service.OrderService;
import org.example.util.DataStorageUtil;

import java.util.*;

public class UserController {
    private User currentAdmin;
    private final Map<Integer, User> users = new HashMap<>();
    private final MenuService menuService;
    private final OrderController orderController;
    private final Scanner scanner = new Scanner(System.in);

    public UserController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderController = new OrderController(orderService, menuService);

        // users Map을 DataStorageUtil을 사용해 로드
        Map<Integer, User> savedUsers = DataStorageUtil.loadUserDataAsMap();
        if (savedUsers != null) {
            users.putAll(savedUsers);  // Map에 데이터를 추가
        }
    }

    public void createAdmin() {
        System.out.println("관리자의 정보를 입력해주세요 (ex: 관리자1, 30000)");
        String input = scanner.nextLine();
        String[] adminData = input.split(",");
        int balance = Integer.parseInt(adminData[1].trim());

        currentAdmin = new User(balance, true);
        System.out.println("관리자가 생성되었습니다.");
        users.put(currentAdmin.getId(), currentAdmin);
        DataStorageUtil.saveUserData(new ArrayList<>(users.values())); // Map을 List로 변환하여 저장
    }

    public void adminLogin() {
        if (currentAdmin == null) {
            System.out.println("[ERROR] 관리자 생성이 필요.");
            return;
        }
        System.out.println("관리자: " + currentAdmin.getId());
    }

    public void createUser() {
        System.out.println("회원의 정보를 입력해주세요 (ex: 1, 30000)");
        String input = scanner.nextLine();
        String[] userData = input.split(",");
        int userId = Integer.parseInt(userData[0].trim());
        int balance = Integer.parseInt(userData[1].trim());

        User user = new User(userId, balance, false);
        users.put(userId, user);
        System.out.println("회원이 생성되었습니다.");
        DataStorageUtil.saveUserData(new ArrayList<>(users.values())); // Map을 List로 변환하여 저장
    }

    public void userLogin() {
        System.out.println("사용자 고유 ID를 입력해주세요: ");
        int userId = Integer.parseInt(scanner.nextLine().trim());

        if (!users.containsKey(userId)) {
            System.out.println("[ERROR] 존재하지 않는 사용자입니다.");
            return;
        }

        User currentUser = users.get(userId);

        if (currentUser == null) {
            System.out.println("[ERROR] 회원이 생성되지 않았습니다.");
            return;
        }

        System.out.println("안녕하세요 회원 " + currentUser.getId() + "님, 햄버거 가게 입니다.");
        System.out.println("현재 접속된 관리자는 관리자" + currentAdmin.getId() + "입니다");

        while (true) {
            menuService.printMenu();
            orderController.processOrder(currentUser, currentAdmin);
            System.out.println("구매하고 싶은 다른 상품이 있나요? (Y/N)");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (!choice.equals("Y")) {
                break;
            }
        }
    }
}