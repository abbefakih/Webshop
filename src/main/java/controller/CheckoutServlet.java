package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Cart;
import model.Product;
import model.User;
import model.OrderItem;

import service.OrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final OrderService orderService =
            new OrderService();


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {


        /*
         * Kontrollera login
         */

        User user =
                (User) request
                        .getSession()
                        .getAttribute("user");

        if (user == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }


        /*
         * Hämta cart
         */

        Cart cart =
                (Cart) request
                        .getSession()
                        .getAttribute("cart");


        if (cart == null || cart.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/cart"
            );

            return;
        }


        /*
         * Skapa OrderItems
         *
         * Din nuvarande Cart innehåller
         * produkter utan separat quantity.
         *
         * Därför behandlar vi varje produkt
         * som quantity = 1.
         */

        List<OrderItem> items =
                new ArrayList<>();


        for (Product product :
                cart.getProducts()) {

            OrderItem item =
                    new OrderItem(
                            0,
                            0,
                            product.getId(),
                            1,
                            product.getPrice()
                    );

            items.add(item);
        }


        /*
         * Genomför checkout
         */

        int orderId =
                orderService.checkout(
                        user.getId(),
                        items
                );

        /*
         * Kontrollera resultat
         */

        if (orderId == -1) {

            request.setAttribute(
                    "error",
                    "Order could not be completed. " +
                            "There may not be enough stock."
            );

            request.getRequestDispatcher(
                    "/cart.jsp"
            ).forward(request, response);

            return;
        }

        /*
         Order lyckades.
         Töm cart.
         */
        request.getSession()
                .removeAttribute("cart");

        response.sendRedirect(
                request.getContextPath()
                        + "/order-success.jsp"
        );
    }
}