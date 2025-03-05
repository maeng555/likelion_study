package org.example.entity;

public class User {
    private final int id;
    private int balance;
    private boolean isAdmin;


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
    public boolean isAdmin() {
        return isAdmin;
    }

    public void pay(int amount) {
        if (balance < amount) {
            throw new IllegalArgumentException("[ERROR] 잔액 부족.");
        }
        balance -= amount;
    }
    public void addPay(int amount) {
        balance += amount; // 관리자 잔액 추가
    }
}
