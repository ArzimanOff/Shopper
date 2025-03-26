package org.arzimanoff.shopper.service.category;

import org.arzimanoff.shopper.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
    List<Category> getAllCategories();
}
