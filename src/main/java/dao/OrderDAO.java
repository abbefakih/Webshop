package dao;

import model.Order;
import model.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    int createOrder(
            Connection connection,
            int userId,
            List<OrderItem> items
    ) throws SQLException;

    List<Order> getOrdersByUser(int userId);

    List<Order> getAllOrders();

    void updateOrderStatus(int orderId, String status);
}
