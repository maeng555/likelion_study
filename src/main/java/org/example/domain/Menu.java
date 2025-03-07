package org.example.domain;

public class Menu {
    private final String name;
    private final int price;
    private int quantity;
    private final String description;
    private final String category;

    public Menu(String name, int price, int quantity, String description, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;

    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void decreaseQuantity(int quantity) throws IllegalArgumentException {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s | %d원 | 재고: %d개 | %s (%s)",
                name, price, quantity, description, category);
    }


}
