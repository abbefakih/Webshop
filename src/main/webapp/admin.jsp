<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User,model.Role" %>
<% User user=(User)session.getAttribute("user"); if(user==null||user.getRole()!=Role.ADMIN){response.sendRedirect(request.getContextPath()+"/login.jsp");return;} %>
<!doctype html><html><head><title>WebShop Admin</title>
<style>body{font-family:Arial;max-width:1000px;margin:40px auto;padding:20px}.grid{display:grid;grid-template-columns:repeat(3,1fr);gap:18px}.card{border:1px solid #ddd;border-radius:12px;padding:20px}.btn{display:inline-block;background:#222;color:white;padding:10px 14px;text-decoration:none;border-radius:7px}nav a{margin-right:15px}</style></head>
<body><h1>Admin Panel</h1><p>Welcome, <b><%=user.getUsername()%></b>!</p>
<div class="grid">
<div class="card"><h2>Products</h2><p>Manage products, prices and stock.</p><a class="btn" href="<%=request.getContextPath()%>/admin-products">Manage Products</a></div>
<div class="card"><h2>Categories</h2><p>Manage product categories.</p><a class="btn" href="<%=request.getContextPath()%>/admin-categories">Manage Categories</a></div>
<div class="card"><h2>Users</h2><p>Manage CUSTOMER, WAREHOUSE and ADMIN accounts.</p><a class="btn" href="<%=request.getContextPath()%>/admin-users">Manage Users</a></div>
</div><hr><nav><a href="<%=request.getContextPath()%>/home.jsp">Home</a><a href="<%=request.getContextPath()%>/products">Products</a><a href="<%=request.getContextPath()%>/logout">Logout</a></nav>
</body></html>
