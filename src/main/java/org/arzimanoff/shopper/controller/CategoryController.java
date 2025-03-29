package org.arzimanoff.shopper.controller;

import org.arzimanoff.shopper.exceptions.AlreadyExistsException;
import org.arzimanoff.shopper.exceptions.ResourceNotFoundException;
import org.arzimanoff.shopper.model.Category;
import org.arzimanoff.shopper.response.ApiResponse;
import org.arzimanoff.shopper.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Список категорий найден!", categories));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка поиска категорий!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(
            @RequestBody Category newcategory
    ){

        try {
            Category category = categoryService.addCategory(newcategory);
            return ResponseEntity.ok(new ApiResponse("Новая категория успешно добавлена!", category));
        } catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка добавления новой категории!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(
            @PathVariable Long categoryId
    ){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Категория успешно найдена!", category));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Не предвиденная ошибка во время поиска категории по id", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getCategoryByName(
            @RequestParam String name
    ){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Категория успешно найдена!", category));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Не предвиденная ошибка во время поиска категории по имени", e.getMessage()));
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(
            @RequestBody Category newCategory,
            @PathVariable Long categoryId
    ){
        try {
            Category category = categoryService.updateCategory(newCategory, categoryId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Категория успешно обновлена!", category));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Не предвиденная ошибка во время обновления категории", e.getMessage()));
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long categoryId
    ){
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Категория успешно удалена!", null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Не предвиденная ошибка во время удаления категории", e.getMessage()));
        }
    }

}
