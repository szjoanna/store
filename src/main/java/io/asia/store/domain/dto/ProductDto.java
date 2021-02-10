package io.asia.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long version;
    private List<ProductDto> listOfProducts;
    private boolean main;
    private Long categoryId;
}
