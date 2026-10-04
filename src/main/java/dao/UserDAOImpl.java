package dao;
import database.DatabaseConnection;
import model.*;
import java.sql.*;
import java.util.*;
public class UserDAOImpl implements UserDAO {
    public User login(String username,String password){
        String sql="SELECT id, username, password, role FROM webshop.users WHERE username=? AND password=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,username); p.setString(2,password);
            try(ResultSet r=p.executeQuery()){ if(r.next()) return map(r); }
        }catch(SQLException e){ throw new RuntimeException("Could not log in user.",e); }
        return null;
    }
    public List<User> getAllUsers(){
        List<User> out=new ArrayList<>();
        String sql="SELECT id, username, password, role FROM webshop.users ORDER BY id";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            while(r.next()) out.add(map(r));
        }catch(SQLException e){ throw new RuntimeException("Could not load users.",e); }
        return out;
    }
    public void addUser(User u){
        String sql="INSERT INTO webshop.users(username,password,role) VALUES(?,?,?)";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,u.getUsername()); p.setString(2,u.getPassword()); p.setString(3,u.getRole().name()); p.executeUpdate();
        }catch(SQLException e){ throw new RuntimeException("Could not add user.",e); }
    }
    public void updateUser(User u){
        String sql="UPDATE webshop.users SET username=?, password=?, role=? WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,u.getUsername()); p.setString(2,u.getPassword()); p.setString(3,u.getRole().name()); p.setInt(4,u.getId()); p.executeUpdate();
        }catch(SQLException e){ throw new RuntimeException("Could not update user.",e); }
    }
    public void deleteUser(int id){
        String sql="DELETE FROM webshop.users WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,id); p.executeUpdate();
        }catch(SQLException e){ throw new RuntimeException("Could not delete user. Existing orders may reference this user.",e); }
    }
    private User map(ResultSet r)throws SQLException{
        return new User(r.getInt("id"),r.getString("username"),r.getString("password"),Role.valueOf(r.getString("role")));
    }
}
