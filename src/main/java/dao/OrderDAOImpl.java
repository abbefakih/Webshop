package dao;

import database.DatabaseConnection;
import model.Order;
import model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int createOrder(
            Connection connection,
            int userId,
            List<OrderItem> items) throws SQLException {

        String orderSql =
                "INSERT INTO webshop.orders (user_id, status) VALUES (?, ?)";

        String itemSql =
                "INSERT INTO webshop.order_items " +
                        "(order_id, product_id, quantity, price) " +
                        "VALUES (?, ?, ?, ?)";

        int orderId;

        try (PreparedStatement orderStatement =
                     connection.prepareStatement(
                             orderSql,
                             Statement.RETURN_GENERATED_KEYS)) {

            orderStatement.setInt(1, userId);
            orderStatement.setString(2, "NEW");

            orderStatement.executeUpdate();

            try (ResultSet keys =
                         orderStatement.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException(
                            "Could not create order."
                    );
                }

                orderId = keys.getInt(1);
            }
        }

        try (PreparedStatement itemStatement =
                     connection.prepareStatement(itemSql)) {

            for (OrderItem item : items) {

                itemStatement.setInt(1, orderId);
                itemStatement.setInt(2, item.getProductId());
                itemStatement.setInt(3, item.getQuantity());
                itemStatement.setDouble(4, item.getPrice());

                itemStatement.addBatch();
            }

            itemStatement.executeBatch();
        }

        return orderId;
    }


    @Override
    public List<Order> getOrdersByUser(int userId) {

        List<Order> orders = new ArrayList<>();

        String sql =
                "SELECT id, user_id, status " +
                        "FROM webshop.orders " +
                        "WHERE user_id = ? " +
                        "ORDER BY order_date DESC";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                orders.add(
                        new Order(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("status")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String sql =
                "SELECT id, user_id, status " +
                        "FROM webshop.orders " +
                        "ORDER BY order_date DESC";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                orders.add(
                        new Order(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("status")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public void updateOrderStatus(
            int orderId,
            String status) {

        String sql =
                "UPDATE webshop.orders SET status = ? WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, orderId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}