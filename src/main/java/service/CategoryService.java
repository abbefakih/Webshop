package service;
import dao.*;
import model.Category;
import java.util.List;
public class CategoryService {
    private final CategoryDAO dao=new CategoryDAOImpl();
    public List<Category> getAllCategories(){return dao.getAllCategories();}
    public void addCategory(Category c){dao.addCategory(c);}
    public void updateCategory(Category c){dao.updateCategory(c);}
    public void deleteCategory(int id){dao.deleteCategory(id);}
}
