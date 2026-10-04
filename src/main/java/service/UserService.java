package service;
import dao.*;
import model.User;
import java.util.List;
public class UserService {
    private final UserDAO userDAO=new UserDAOImpl();
    public User login(String username,String password){return userDAO.login(username,password);}
    public List<User> getAllUsers(){return userDAO.getAllUsers();}
    public void addUser(User u){userDAO.addUser(u);}
    public void updateUser(User u){userDAO.updateUser(u);}
    public void deleteUser(int id){userDAO.deleteUser(id);}
}
