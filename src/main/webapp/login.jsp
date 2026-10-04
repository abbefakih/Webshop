<%@ page contentType="text/html;charset=UTF-8" %>
<% String e=request.getParameter("error"); %>
<!doctype html><html><head><title>Login</title></head><body><h1>Login</h1>
<%if(e!=null){%><p style="color:red"><%=e%></p><%}%>
<form action="<%=request.getContextPath()%>/login" method="post"><label>Username</label><br><input name="username" required><br><br><label>Password</label><br><input type="password" name="password" required><br><br><button>Login</button></form>
<p><a href="<%=request.getContextPath()%>/index.jsp">Back</a></p></body></html>
