package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(String name, String description, double price, String category, String imageUrl);
    Product replaceProduct(long id, String name, String description, double price, String category, String imageUrl) throws ProductNotFoundException;
}
