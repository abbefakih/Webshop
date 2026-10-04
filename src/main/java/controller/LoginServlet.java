package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.UserService;
import java.io.IOException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;
    public void init(){service=new UserService();}
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        String u=req.getParameter("username"),p=req.getParameter("password");
        if(u==null||p==null||u.isBlank()||p.isBlank()){res.sendRedirect(req.getContextPath()+"/login.jsp?error=Enter+username+and+password");return;}
        try{
            User user=service.login(u.trim(),p);
            if(user==null){res.sendRedirect(req.getContextPath()+"/login.jsp?error=Wrong+username+or+password");return;}
            req.getSession(true).setAttribute("user",user);
            if(user.getRole()==Role.ADMIN)res.sendRedirect(req.getContextPath()+"/admin.jsp");
            else if(user.getRole()==Role.WAREHOUSE)res.sendRedirect(req.getContextPath()+"/warehouse");
            else res.sendRedirect(req.getContextPath()+"/products");
        }catch(RuntimeException e){throw new ServletException("Database login failed.",e);}
    }
}
