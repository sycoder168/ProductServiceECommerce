package com.scaler.productservicecapstone.dtos;

import com.scaler.productservicecapstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private long id;
    private String name;
    private double price;
    private String description;
    private String category;
    private String imageUrl;

    public static ProductResponseDto from(Product product) {
        if (product == null) return null;

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory().getName());
        dto.setImageUrl(product.getImageUrl());

        return dto;
    }
}
