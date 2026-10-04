<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="model.Cart" %>
<%@ page import="model.Product" %>

<html>

<head>
    <title>Shopping Cart</title>
</head>

<body>

<h1>Shopping Cart</h1>

<a href="${pageContext.request.contextPath}/products">
    ← Continue Shopping
</a>

<br>
<br>

<%
    Cart cart =
            (Cart) request.getAttribute("cart");

    if (cart != null && !cart.isEmpty()) {
%>

<table border="1">

    <tr>
        <th>Product</th>
        <th>Price</th>
        <th>Action</th>
    </tr>

<%
    for (Product product : cart.getProducts()) {
%>

    <tr>

        <td>
            <%= product.getName() %>
        </td>

        <td>
            <%= product.getPrice() %> kr
        </td>

        <td>

            <form action="<%= request.getContextPath() %>/cart"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="remove">

                <input type="hidden"
                       name="productId"
                       value="<%= product.getId() %>">

                <button type="submit">
                    Remove
                </button>

            </form>

        </td>

    </tr>

<%
    }
%>

</table>

<br>

<h3>
    Total:
    <%= cart.getTotal() %> kr
</h3>

<form action="<%= request.getContextPath() %>/checkout"
      method="post">

    <button type="submit">
        Checkout
    </button>

</form>

<%
    } else {
%>

<p>
    Your shopping cart is empty.
</p>

<%
    }
%>

</body>

</html>