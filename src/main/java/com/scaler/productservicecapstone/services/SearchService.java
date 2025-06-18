package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.models.Product;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Product> search(String query, int pageNumber, int pageSize, String sortParam);
}
