package org.example.domain;

public class User {

    private static int idCounter = 1;
    private int id;
    private int balance;
    private final boolean isAdmin;

    public User(int balance, boolean isAdmin) {
        this.id = isAdmin ? idCounter++ : -1;  // 관리자만 자동 증가
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public User(int id, int balance, boolean isAdmin) {
        this.id = id;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void pay(int amount) {
        if (isAdmin) {
            balance += amount;
        } else {
            if (balance < amount) {
                throw new IllegalArgumentException("[ERROR] 잔액 부족.");
            }
            balance -= amount;
        }
    }
}
