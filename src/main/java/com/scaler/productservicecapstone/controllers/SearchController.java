package com.scaler.productservicecapstone.controllers;

import com.scaler.productservicecapstone.dtos.ProductResponseDto;
import com.scaler.productservicecapstone.dtos.SearchRequestDto;
import com.scaler.productservicecapstone.models.Product;
import com.scaler.productservicecapstone.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public Page<ProductResponseDto> search(@RequestBody SearchRequestDto searchRequestDto) {
        Page<Product> products = searchService.search(searchRequestDto.getQuery(),
                                      searchRequestDto.getPageNumber(),
                              searchRequestDto.getPageSize(), searchRequestDto.getSortParam());
        return products.map(ProductResponseDto::from);
    }

    @GetMapping("/search")
    public Page<ProductResponseDto> search(@RequestParam String query,
                                @RequestParam int pageNumber,
                                @RequestParam int pageSize,
                                @RequestParam String sortParam) {
        Page<Product> products = searchService.search(query, pageNumber, pageSize, sortParam);
        return products.map(ProductResponseDto::from);

    }
}
