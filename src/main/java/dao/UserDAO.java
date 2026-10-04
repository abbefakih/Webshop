package dao;
import model.User;
import java.util.List;
public interface UserDAO {
    User login(String username,String password);
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}
