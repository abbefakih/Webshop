jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,model.Order,model.User,model.Role" %>

<%
    User u = (User) session.getAttribute("user");

    if (u == null || u.getRole() != Role.WAREHOUSE) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Order> os = (List<Order>) request.getAttribute("orders");
%>

<!doctype html>

<html>
<head>
    <title>Warehouse</title>
</head>

<body>

<h1>Warehouse</h1>

<p>Welcome, <%= u.getUsername() %>!</p>

<table border="1" cellpadding="8">

    <tr>
        <th>Order</th>
        <th>User</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <% for (Order o : os) { %>

    <tr>

        <td>
            <%= o.getId() %>
        </td>

        <td>
            <%= o.getUserId() %>
        </td>

        <td>
            <%= o.getStatus() %>
        </td>

        <td>

            <% if ("NEW".equals(o.getStatus())) { %>

                <form action="<%= request.getContextPath() %>/warehouse"
                      method="post">

                    <input type="hidden"
                           name="orderId"
                           value="<%= o.getId() %>">

                    <button type="submit">
                        Packa
                    </button>

                </form>

            <% } else { %>

                Packed

            <% } %>

        </td>

    </tr>

    <% } %>

</table>

<p>
    <a href="<%= request.getContextPath() %>/home.jsp">Home</a>
    |
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</p>

</body>
</html>