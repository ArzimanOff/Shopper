package org.arzimanoff.shopper.controller;


import org.arzimanoff.shopper.exceptions.AlreadyExistsException;
import org.arzimanoff.shopper.exceptions.ProductNotFoundException;
import org.arzimanoff.shopper.model.Category;
import org.arzimanoff.shopper.model.Product;
import org.arzimanoff.shopper.request.AddProductRequest;
import org.arzimanoff.shopper.request.UpdateProductRequest;
import org.arzimanoff.shopper.response.ApiResponse;
import org.arzimanoff.shopper.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Список продуктов найден!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка поиска продуктов!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(
            @RequestBody AddProductRequest request
    ) {
        try {
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("Новый продукт успешно добавлен!", product));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка добавления нового продукта!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId
    ) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Продукт успешно удалён!", null));
        } catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Ошибка удаления продукта!", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка удаления продукта!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody UpdateProductRequest request,
            @PathVariable Long productId
    ) {
        try {
            Product product = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Продукт успешно обновлён!", product));
        } catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Ошибка обновления продукта!", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка обновления продукта!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Поиск по бренду и имени успешно произведён!", count));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка поиска по бренду и имени!", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }



}
