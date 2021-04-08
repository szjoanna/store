package io.asia.store.mapper;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dto.ProductDto;

import java.util.List;

public interface ProductMapper {
    Product toDao(ProductDto productDto);
    ProductDto toDto(Product product);
    List<ProductDto> listProductsToDto(List<Product> productList);
}
