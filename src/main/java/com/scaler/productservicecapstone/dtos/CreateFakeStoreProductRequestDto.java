package com.scaler.productservicecapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreProductRequestDto {
    private String name;
    private double price;
    private String description;
    private String category;
    private String imageUrl;
}
