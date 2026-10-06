package controller;

import dto.OrderDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Role;
import model.User;
import service.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {

    private final OrderService orders =
            new OrderService();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        User u =
                (User) req.getSession()
                        .getAttribute("user");

        if (u == null ||
                u.getRole() != Role.WAREHOUSE) {

            res.sendRedirect(
                    req.getContextPath() +
<<<<<<< ours
                    "/login.jsp"
=======
                            "/login.jsp"
>>>>>>> theirs
            );

            return;
        }

        List<OrderDTO> orderDTOs =
                orders.getAllOrders();

        req.setAttribute(
                "orders",
                orderDTOs
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

        User u =
                (User) req.getSession()
                        .getAttribute("user");

        if (u == null ||
                u.getRole() != Role.WAREHOUSE) {

            res.sendRedirect(
                    req.getContextPath() +
<<<<<<< ours
                    "/login.jsp"
=======
                            "/login.jsp"
>>>>>>> theirs
            );

            return;
        }

        String orderIdParameter =
                req.getParameter("orderId");

        if (orderIdParameter != null) {

            int orderId =
                    Integer.parseInt(
                            orderIdParameter
                    );

            orders.updateOrderStatus(
                    orderId,
                    "PACKED"
            );
        }

        /*
         * Post Redirect Get
         */
        res.sendRedirect(
                req.getContextPath() +
<<<<<<< ours
                "/warehouse"
=======
                        "/warehouse"
>>>>>>> theirs
        );
    }
}
