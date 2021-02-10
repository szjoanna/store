package io.asia.store.mapper;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dto.ProductDto;

public interface ProductMapper {
    Product toDao(ProductDto productDto);
    ProductDto toDto(Product product);
}
