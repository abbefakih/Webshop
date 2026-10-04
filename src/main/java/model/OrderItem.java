package model;

public class OrderItem {

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderItem(
            int id,
            int orderId,
            int productId,
            int quantity,
            double price) {

        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}