package com.scaler.productservicecapstone.services;

import com.scaler.productservicecapstone.dtos.FakeStoreRequestDto;
import com.scaler.productservicecapstone.dtos.FakeStoreResponseDto;
import com.scaler.productservicecapstone.exceptions.ProductNotFoundException;
import com.scaler.productservicecapstone.models.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;
    RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {

        Product productFromRedis = (Product) redisTemplate.opsForValue().get(String.valueOf(id));

        if (productFromRedis != null) {
            return productFromRedis;
        }

        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreResponseDto.class
        );

        if (fakeStoreResponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        Product productFromFakeStore = fakeStoreResponseDto.toProduct();
        redisTemplate.opsForValue().set(String.valueOf(id), productFromFakeStore);
        return productFromFakeStore;
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        FakeStoreResponseDto[] fakeStoreResponseDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products/",
                FakeStoreResponseDto[].class
        );

        if (fakeStoreResponseDtos == null) {
            throw new ProductNotFoundException("There are no products in the store");
        }

        List<Product> products = new ArrayList<>();
        for (FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos) {
            products.add(fakeStoreResponseDto.toProduct());
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String category, String imageUrl) {
        FakeStoreRequestDto fakeStoreRequestDto = createDtoFromParams(name, description, price, category, imageUrl);

        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreRequestDto, FakeStoreResponseDto.class);

        return fakeStoreResponseDto.toProduct();

    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String category, String imageUrl) throws ProductNotFoundException {
        FakeStoreRequestDto updatedFakeStoreRequestDto = createDtoFromParams(name, description, price, category, imageUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> entity = new HttpEntity<>(updatedFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreResponseDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT,
                entity,
                FakeStoreResponseDto.class,
                id
        );

        return responseEntity.getBody().toProduct();

    }

    private FakeStoreRequestDto createDtoFromParams(String name, String description, double price, String category, String imageUrl) {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setCategory(category);
        fakeStoreRequestDto.setImage(imageUrl);
        return fakeStoreRequestDto;
    }

}
