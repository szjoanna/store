package io.asia.store.mapper;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BasicMapper<ProductDto, Product>{
    Product toDao(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> listProductsToDto(List<Product> productList);
}
