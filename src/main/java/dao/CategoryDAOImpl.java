package dao;
import database.DatabaseConnection;
import model.Category;
import java.sql.*;
import java.util.*;
public class CategoryDAOImpl implements CategoryDAO {
    public List<Category> getAllCategories(){
        List<Category> out=new ArrayList<>();
        String sql="SELECT id,name,description FROM webshop.categories ORDER BY id";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            while(r.next()) out.add(new Category(r.getInt("id"),r.getString("name"),r.getString("description")));
        }catch(SQLException e){throw new RuntimeException("Could not load categories.",e);}
        return out;
    }
    public Category getCategoryById(int id){
        String sql="SELECT id,name,description FROM webshop.categories WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,id); try(ResultSet r=p.executeQuery()){if(r.next())return new Category(r.getInt("id"),r.getString("name"),r.getString("description"));}
        }catch(SQLException e){throw new RuntimeException("Could not load category.",e);}
        return null;
    }
    public void addCategory(Category x){
        String sql="INSERT INTO webshop.categories(name,description) VALUES(?,?)";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){p.setString(1,x.getName());p.setString(2,x.getDescription());p.executeUpdate();}
        catch(SQLException e){throw new RuntimeException("Could not add category.",e);}
    }
    public void updateCategory(Category x){
        String sql="UPDATE webshop.categories SET name=?,description=? WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){p.setString(1,x.getName());p.setString(2,x.getDescription());p.setInt(3,x.getId());p.executeUpdate();}
        catch(SQLException e){throw new RuntimeException("Could not update category.",e);}
    }
    public void deleteCategory(int id){
        String sql="DELETE FROM webshop.categories WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);p.executeUpdate();}
        catch(SQLException e){throw new RuntimeException("Could not delete category. Products may still use it.",e);}
    }
}
