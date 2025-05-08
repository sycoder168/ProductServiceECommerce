package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;

import java.util.List;


public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(String name, String description, double price, String category, String imageUrl);
    Product udpateProduct(long id, String name, String description, double price, String category, String imageUrl) throws ProductNotFoundException;
}
