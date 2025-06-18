package com.scaler.productservicecapstone.controllers;

import com.scaler.productservicecapstone.dtos.CreateFakeStoreProductRequestDto;
import com.scaler.productservicecapstone.dtos.ProductResponseDto;
import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;
import com.scaler.productservicecapstone.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);

        return ProductResponseDto.from(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productService.getAllProducts();
        return products.stream().map(ProductResponseDto::from).collect(Collectors.toList());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) {
        Product product = productService.createProduct(
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getCategory(),
                createFakeStoreProductRequestDto.getImageUrl()
        );
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable long id, @RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) throws ProductNotFoundException {
        Product product = productService.replaceProduct(
                id,
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getCategory(),
                createFakeStoreProductRequestDto.getImageUrl()
        );

        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.OK);

    }


    @PostMapping("/products/batch")
    public ResponseEntity<List<ProductResponseDto>> createMultipleProducts(
            @RequestBody List<CreateFakeStoreProductRequestDto> productDtos) {

        List<Product> createdProducts = productDtos.stream()
                .map(dto -> productService.createProduct(
                        dto.getName(),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getCategory(),
                        dto.getImageUrl()
                ))
                .toList();

        List<ProductResponseDto> responseDtos = createdProducts.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtos, HttpStatus.CREATED);
    }
}
