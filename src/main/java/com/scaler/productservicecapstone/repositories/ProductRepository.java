package com.scaler.productservicecapstone.repositories;

import com.scaler.productservicecapstone.dtos.ProductProjection;
import com.scaler.productservicecapstone.dtos.ProductProjectionDto;
import com.scaler.productservicecapstone.models.Category;
import com.scaler.productservicecapstone.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
    List<Product> findByCategory_Name(String categoryName);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> getProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = CustomNativeQuery.GET_PRODUCT_FROM_CATEGORY_NAME, nativeQuery = true)
    List<Product> getProductsByCategoryNameNative(@Param("categoryName") String categoryName);

    @Query("select p.name, p.price from Product p where p.category.name = :categoryName")
    List<ProductProjection> getProjectedProduct(@Param("categoryName") String categoryName);

    @Query("select new com.scaler.productservicecapstone.dtos.ProductProjectionDto(p.name, p.price) from Product p where p.category.name = :categoryName")
    List<ProductProjectionDto> getProjectedProductDto(@Param("categoryName") String categoryName);

    Page<Product> findByNameContaining(String query, Pageable pageable);
}
