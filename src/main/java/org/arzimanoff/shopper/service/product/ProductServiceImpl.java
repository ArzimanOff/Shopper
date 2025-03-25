package org.arzimanoff.shopper.service.product;

import org.arzimanoff.shopper.exceptions.ProductNotFoundException;
import org.arzimanoff.shopper.model.Product;
import org.arzimanoff.shopper.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Такой продукт не найден")
                );
    }

    @Override
    public void deleteProduct(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> {
                            throw  new ProductNotFoundException("Такой продукт не найден");
                        }
                );
    }

    @Override
    public void updateProduct(Product newProduct, Long productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return repository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return repository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return repository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return repository.countByBrandAndName(brand, name);
    }
}
