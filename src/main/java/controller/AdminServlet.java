package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import service.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet({"/admin-products","/admin-categories","/admin-users","/logout"})
public class AdminServlet extends HttpServlet {
    private ProductService products; private CategoryService categories; private UserService users;
    public void init(){products=new ProductService();categories=new CategoryService();users=new UserService();}

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        String path=req.getServletPath();
        if("/logout".equals(path)){logout(req,res);return;}
        if(!admin(req)){res.sendRedirect(req.getContextPath()+"/login.jsp");return;}
        try{
            if("/admin-products".equals(path)){req.setAttribute("products",products.getAllProducts());req.setAttribute("categories",categories.getAllCategories());req.getRequestDispatcher("/admin-products.jsp").forward(req,res);}
            else if("/admin-categories".equals(path)){req.setAttribute("categories",categories.getAllCategories());req.getRequestDispatcher("/admin-categories.jsp").forward(req,res);}
            else if("/admin-users".equals(path)){req.setAttribute("users",users.getAllUsers());req.getRequestDispatcher("/admin-users.jsp").forward(req,res);}
            else res.sendError(404);
        }catch(RuntimeException e){throw new ServletException(root(e),e);}
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
        String path=req.getServletPath();
        if("/logout".equals(path)){logout(req,res);return;}
        if(!admin(req)){res.sendRedirect(req.getContextPath()+"/login.jsp");return;}
        try{
            if("/admin-products".equals(path))product(req);
            else if("/admin-categories".equals(path))category(req);
            else if("/admin-users".equals(path))user(req);
            else {res.sendError(404);return;}
            res.sendRedirect(req.getContextPath()+path);
        }catch(Exception e){
            String msg=URLEncoder.encode(root(e),StandardCharsets.UTF_8);
            res.sendRedirect(req.getContextPath()+path+"?error="+msg);
        }
    }
    private void product(HttpServletRequest r){
        String a=r.getParameter("action");
        if("add".equals(a))products.addProduct(new Product(0,r.getParameter("name"),Double.parseDouble(r.getParameter("price")),Integer.parseInt(r.getParameter("stock")),Integer.parseInt(r.getParameter("categoryId"))));
        else if("update".equals(a))products.updateProduct(new Product(Integer.parseInt(r.getParameter("id")),r.getParameter("name"),Double.parseDouble(r.getParameter("price")),Integer.parseInt(r.getParameter("stock")),Integer.parseInt(r.getParameter("categoryId"))));
        else if("delete".equals(a))products.deleteProduct(Integer.parseInt(r.getParameter("id")));
    }
    private void category(HttpServletRequest r){
        String a=r.getParameter("action"); String d=r.getParameter("description");
        if("add".equals(a))categories.addCategory(new Category(0,r.getParameter("name"),d));
        else if("update".equals(a))categories.updateCategory(new Category(Integer.parseInt(r.getParameter("id")),r.getParameter("name"),d));
        else if("delete".equals(a))categories.deleteCategory(Integer.parseInt(r.getParameter("id")));
    }
    private void user(HttpServletRequest r){
        String a=r.getParameter("action");
        if("add".equals(a))users.addUser(new User(0,r.getParameter("username"),r.getParameter("password"),Role.valueOf(r.getParameter("role"))));
        else if("update".equals(a))users.updateUser(new User(Integer.parseInt(r.getParameter("id")),r.getParameter("username"),r.getParameter("password"),Role.valueOf(r.getParameter("role"))));
        else if("delete".equals(a)){
            int id=Integer.parseInt(r.getParameter("id")); User me=(User)r.getSession().getAttribute("user");
            if(me!=null&&me.getId()==id)throw new IllegalArgumentException("You cannot delete the logged-in admin.");
            users.deleteUser(id);
        }
    }
    private boolean admin(HttpServletRequest r){HttpSession s=r.getSession(false);if(s==null)return false;User u=(User)s.getAttribute("user");return u!=null&&u.getRole()==Role.ADMIN;}
    private void logout(HttpServletRequest r,HttpServletResponse p)throws IOException{HttpSession s=r.getSession(false);if(s!=null)s.invalidate();p.sendRedirect(r.getContextPath()+"/index.jsp");}
    private String root(Exception e){Throwable t=e;while(t.getCause()!=null)t=t.getCause();return t.getMessage()==null?e.toString():t.getMessage();}
}
