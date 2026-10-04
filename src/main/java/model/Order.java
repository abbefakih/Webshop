package model;

public class Order {

    private int id;
    private int userId;
    private String status;

    public Order(int id, int userId, String status) {
        this.id = id;
        this.userId = userId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }
}