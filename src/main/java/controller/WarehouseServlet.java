package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Role;
import model.User;
import service.OrderService;

import java.io.IOException;

@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {

    private final OrderService orders = new OrderService();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("user");

        if (u == null || u.getRole() != Role.WAREHOUSE) {
            res.sendRedirect(
                    req.getContextPath() + "/login.jsp"
            );
            return;
        }

        req.setAttribute(
                "orders",
                orders.getAllOrders()
        );

        req.getRequestDispatcher(
                "/warehouse.jsp"
        ).forward(req, res);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("user");

        if (u == null || u.getRole() != Role.WAREHOUSE) {
            res.sendRedirect(
                    req.getContextPath() + "/login.jsp"
            );
            return;
        }

        String orderIdParameter =
                req.getParameter("orderId");

        if (orderIdParameter != null) {

            int orderId =
                    Integer.parseInt(orderIdParameter);

            orders.updateOrderStatus(
                    orderId,
                    "PACKED"
            );
        }

        res.sendRedirect(
                req.getContextPath() + "/warehouse"
        );
    }
}