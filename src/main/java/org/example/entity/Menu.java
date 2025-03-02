package org.example.entity;

public class Menu {
    private final String name;
    private final String price;
    private final String description;
    private final String category;
    private int amount;
    public Menu(String name, String price, String description, String category, int amount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public int getAmount() {
        return amount;
    }

    public boolean isSoldOut() {
        return amount <= 0;
    }
    public void decreaseAmount(int amount) throws IllegalAccessException {
        if (this.amount >= amount) {
            this.amount -= amount;
        }else{
            throw new IllegalAccessException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

}
