package org.example.domain;

public class Menu {
    private final String name;
    private final int price;
    private int stock;
    private final String description;
    private final String category;

    public Menu(String name, int price, int stock, String description, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseQuantity(int quantity) throws IllegalArgumentException {
        if (this.stock >= quantity) {
            this.stock -= quantity;
        } else {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s | %d원 | 재고: %d개 | %s (%s)",
                name, price, stock, description, category);
    }
}