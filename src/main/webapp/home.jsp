<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User,model.Role" %>
<% User u=(User)session.getAttribute("user"); %>
<!doctype html><html><head><title>WebShop Home</title></head><body><h1>WebShop</h1>
<%if(u==null){%><p>Welcome!</p><a href="<%=request.getContextPath()%>/login.jsp">Login</a><%}else{%>
<h2>Welcome, <%=u.getUsername()%>!</h2><p>Role: <b><%=u.getRole()%></b></p>
<a href="<%=request.getContextPath()%>/products">Products</a>
<%if(u.getRole()==Role.CUSTOMER){%> | <a href="<%=request.getContextPath()%>/cart">Cart</a><%}%>
<%if(u.getRole()==Role.ADMIN){%> | <a href="<%=request.getContextPath()%>/admin.jsp">Admin Panel</a><%}%>
<%if(u.getRole()==Role.WAREHOUSE){%> | <a href="<%=request.getContextPath()%>/warehouse">Warehouse</a><%}%>
 | <a href="<%=request.getContextPath()%>/logout">Logout</a>
<%}%></body></html>
