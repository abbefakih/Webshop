package service;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dto.OrderDTO;
import model.Order;
import model.OrderItem;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAOImpl();
    }

    public int checkout(
            int userId,
            List<OrderItem> items) {

        if (items == null || items.isEmpty()) {
            return -1;
        }

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                /*
                 * 1. Kontrollera och minska lager
                 */
                for (OrderItem item : items) {

                    String stockSql =
                            "UPDATE webshop.products " +
                            "SET stock = stock - ? " +
                            "WHERE id = ? " +
                            "AND stock >= ?";

                    try (var statement =
                                 connection.prepareStatement(stockSql)) {

                        statement.setInt(
                                1,
                                item.getQuantity()
                        );

                        statement.setInt(
                                2,
                                item.getProductId()
                        );

                        statement.setInt(
                                3,
                                item.getQuantity()
                        );

                        int updated =
                                statement.executeUpdate();

                        if (updated == 0) {

                            connection.rollback();

                            return -1;
                        }
                    }
                }

                /*
                 * 2. Skapa order
                 */
                int orderId =
                        orderDAO.createOrder(
                                connection,
                                userId,
                                items
                        );

                /*
                 * 3. Allt lyckades
                 */
                connection.commit();

                return orderId;

            } catch (Exception e) {

                connection.rollback();

                e.printStackTrace();

                return -1;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return -1;
        }
    }

    /*
     * Hämtar orders från DAO och
     * omvandlar Model -> DTO.
     */
    public List<OrderDTO> getAllOrders() {

        List<Order> orders =
                orderDAO.getAllOrders();

        List<OrderDTO> orderDTOs =
                new ArrayList<>();

        for (Order order : orders) {

            orderDTOs.add(
                    new OrderDTO(
                            order.getId(),
                            order.getUserId(),
                            order.getStatus()
                    )
            );
        }

        return orderDTOs;
    }

    public void updateOrderStatus(
            int orderId,
            String status) {

        orderDAO.updateOrderStatus(
                orderId,
                status
        );
    }
}
