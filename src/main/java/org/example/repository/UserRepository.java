package org.example.repository;

import org.example.domain.User;
import org.example.util.DataStorageUtil;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = DataStorageUtil.loadUserData();
        if (this.users == null) {
            this.users = List.of(); // 기본 데이터 설정
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findById(int userId) {
        return users.stream().filter(user -> user.getId() == userId).findFirst();
    }

    public void addUser(User user) {
        users.add(user);
        DataStorageUtil.saveUserData(users);
    }

    public void updateUser(User user) {
        DataStorageUtil.saveUserData(users); // 변경 사항 저장
    }
}