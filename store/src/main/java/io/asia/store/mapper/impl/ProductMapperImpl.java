package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toDao(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .version(productDto.getVersion())
                .main(productDto.isMain())
                .quantity(productDto.getQuantity())
                .deleted(productDto.isDeleted())
                .imageUrl(productDto.getImageUrl())
                .build();
    }

    @Override
    public ProductDto toDto(Product product) {
        List<ProductDto> listOfProducts = null;
        if(product.getListOfProducts() != null) {
            listOfProducts = product.getListOfProducts().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .version(product.getVersion())
                .main(product.isMain())
                .quantity(product.getQuantity())
                .deleted(product.isDeleted())
                .categoryId(product.getCategory().getId())
                .imageUrl(product.getImageUrl())
                .subProducts(listOfProducts)
                .build();
    }

    @Override
    public List<ProductDto> listProductsToDto(List<Product> productList) {
        return productList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
