package com.scaler.productservicecapstone.controllers;

import com.scaler.productservicecapstone.dtos.ErrorDto;
import com.scaler.productservicecapstone.dtos.ProductResponseDto;
import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;
import com.scaler.productservicecapstone.services.ProductService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);

        return ProductResponseDto.from(product);
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ErrorDto handleNullPointerException() {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage("Product cannot be null");
//        errorDto.setStatus("Failure");
//        return errorDto;
//    }
}
