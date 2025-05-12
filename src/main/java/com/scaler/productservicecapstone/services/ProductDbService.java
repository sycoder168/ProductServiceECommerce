package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Category;
import com.scaler.productservicecapstone.models.Product;
import com.scaler.productservicecapstone.repositories.CategoryRepository;
import com.scaler.productservicecapstone.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDbService")
public class ProductDbService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDbService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with the id " + id + " not found"));
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return allProducts;
    }

    @Override
    public Product createProduct(String name, String description, double price, String category, String imageUrl) {

        Product product = buildProduct(name, description, price, category, imageUrl);

        return productRepository.save(product);

    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String category, String imageUrl) throws ProductNotFoundException {
        Product product = buildProduct(name, description, price, category, imageUrl);
        product.setId(id);

        return productRepository.save(product);
    }

    private Product buildProduct(String name, String description, double price, String category, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDb(category);
        product.setCategory(categoryObj);

        return product;
    }

    private Category getCategoryFromDb(String category) {
        Optional<Category> categoryObj = categoryRepository.findByName(category);
        if (categoryObj.isPresent()) {
            return categoryObj.get();
        } else {
            Category newCategory = new Category();
            newCategory.setName(category);
            return categoryRepository.save(newCategory);
        }
    }
}
