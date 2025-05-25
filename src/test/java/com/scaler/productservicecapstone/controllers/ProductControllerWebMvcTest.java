package com.scaler.productservicecapstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.productservicecapstone.dtos.CreateFakeStoreProductRequestDto;
import com.scaler.productservicecapstone.dtos.ProductResponseDto;
import com.scaler.productservicecapstone.models.Category;
import com.scaler.productservicecapstone.models.Product;
import com.scaler.productservicecapstone.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest {

    @MockitoBean
    @Qualifier("productDbService")
    private ProductService productService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Product getProductForTest(){
        Product dummyProduct = new Product();
        dummyProduct.setId(1L);
        dummyProduct.setName("Product 1");
        dummyProduct.setDescription("Product 1 description");
        dummyProduct.setPrice(12.5);
        dummyProduct.setImageUrl("img.url.dummy");

        Category dummyCategory = new Category();
        dummyCategory.setId(1L);
        dummyCategory.setName("Category 1");

        dummyProduct.setCategory(dummyCategory);

        return dummyProduct;
    }

    @Test
    public void testGetAllProductsRunsSuccessfully() throws Exception {
        //arrange
        Product dummyProduct1 = getProductForTest();
        Product dummyProduct2 = getProductForTest();
        dummyProduct2.setId(2L);
        List<Product> dummyProducts = List.of(dummyProduct1, dummyProduct2);

        List<ProductResponseDto> dummyProductResponseDtos = dummyProducts.stream().map(ProductResponseDto::from).toList();

        when(productService.getAllProducts()).thenReturn(dummyProducts);

        //act and assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(dummyProductResponseDtos)));

    }

    @Test
    public void testCreateProductRunsSuccessfully() throws Exception {
        CreateFakeStoreProductRequestDto dummyRequestDto = new CreateFakeStoreProductRequestDto();
        dummyRequestDto.setName("Product 1");
        dummyRequestDto.setDescription("Product 1 description");
        dummyRequestDto.setImageUrl("img.url.dummy");
        dummyRequestDto.setPrice(12.5);
        dummyRequestDto.setCategory("Category 1");

        Product productAfterSave = getProductForTest();
        ProductResponseDto dummyResponseDto = ProductResponseDto.from(productAfterSave);

        when(productService.createProduct(
                dummyRequestDto.getName(),
                dummyRequestDto.getDescription(),
                dummyRequestDto.getPrice(),
                dummyRequestDto.getCategory(),
                dummyRequestDto.getImageUrl())
        ).thenReturn(productAfterSave);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(dummyResponseDto)));
    }

}
