package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;


public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
}
