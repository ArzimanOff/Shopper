package org.arzimanoff.shopper.service.category;

import lombok.RequiredArgsConstructor;
import org.arzimanoff.shopper.exceptions.AlreadyExistsException;
import org.arzimanoff.shopper.exceptions.ResourceNotFoundException;
import org.arzimanoff.shopper.model.Category;
import org.arzimanoff.shopper.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Такой категории не существует"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException("Категория \"" + category.getName() + "\" уже существует."));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Такой категории не существует"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Такой категории не существует");
                        }
                );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
