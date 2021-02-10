package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


//http://localhost:8080/swagger-ui.html#/

@Component
public class ProductMapperImpl implements ProductMapper {
    //builder to zworzec projektowy, który tworzy obikety w sposób łatwiejszy dla developera
    //obiekt DTO obiekt do komunikacji z użytkownikiem
    //obiekt DAO do komunikacji z bazą danych
    //oddziela sie to ze wzg bezpieczenstwa oraz niweluje bledu stack overflow

    @Override
    public Product toDao(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .version(productDto.getVersion())
                .main(productDto.isMain())
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
                .listOfProducts(listOfProducts)
                .build();
    }
}
