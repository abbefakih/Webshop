package model;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int categoryId;

    public Product(
            int id,
            String name,
            double price,
            int stock,
            int categoryId) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getCategoryId() {
        return categoryId;
    }
}