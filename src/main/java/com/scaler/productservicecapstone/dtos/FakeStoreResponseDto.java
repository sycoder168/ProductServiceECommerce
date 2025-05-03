package com.scaler.productservicecapstone.dtos;

import com.scaler.productservicecapstone.models.Category;
import com.scaler.productservicecapstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreResponseDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;

    public Product toProduct() {

        Product product = new Product();
        product.setId(id);
        product.setName(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category productCategory = new Category();
        productCategory.setName(category);

        product.setCategory(productCategory);

        return product;
    }
}
