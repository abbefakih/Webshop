<%@ page contentType="text/html;charset=UTF-8" %>

<html>

<head>
    <title>Products</title>
</head>

<body>

<h1>Products</h1>

<a href="${pageContext.request.contextPath}/cart">
    🛒 View Cart
</a>

<br>
<br>

<table border="1">

    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Category ID</th>
        <th>Action</th>
    </tr>

    <%
        java.util.List<model.Product> products =
                (java.util.List<model.Product>)
                        request.getAttribute("products");

        if (products != null && !products.isEmpty()) {

            for (model.Product product : products) {
    %>

    <tr>

        <td>
            <%= product.getId() %>
        </td>

        <td>
            <%= product.getName() %>
        </td>

        <td>
            <%= product.getPrice() %> kr
        </td>

        <td>
            <%= product.getStock() %>
        </td>

        <td>
            <%= product.getCategoryId() %>
        </td>

        <td>

            <form action="<%= request.getContextPath() %>/cart"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="add">

                <input type="hidden"
                       name="productId"
                       value="<%= product.getId() %>">

                <button type="submit">
                    Add to Cart
                </button>

            </form>

        </td>

    </tr>

    <%
            }

        } else {
    %>

    <tr>
        <td colspan="6">
            No products available.
        </td>
    </tr>

    <%
        }
    %>

</table>

</body>

</html>