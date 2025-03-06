package org.example.entity;

public class Menu {
    private final String name;
    private final int price;
    private int quantity;
    private final String description;
    private final String category;

    public Menu(String name, int price, int quantity ,String description, String category) {
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
    public String getDescription() {
        return description;
    }
    public String getCategory() {
        return category;
    }
    public int getQuantity() {
        return quantity;
    }

    public boolean isSoldOut() {
        return quantity <= 0;
    }
    public void decreaseQuantity(int quantity) throws IllegalAccessException {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        }else{
            throw new IllegalAccessException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    // toString() 오버라이딩 추가 메모리주소값 출력 변환
    @Override
    public String toString() {
        return String.format("%s | %d원 | 재고: %d개 | %s (%s)",
                name, price, quantity, description, category);
    }


}
