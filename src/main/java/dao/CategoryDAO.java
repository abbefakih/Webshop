package dao;

import model.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);
}