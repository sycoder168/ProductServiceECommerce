package com.scaler.productservicecapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreRequestDto {
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
}
