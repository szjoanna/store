package io.asia.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductDto {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Double price;
    private Long version;
    private List<ProductDto> subProducts;
    private boolean main;
    private Long categoryId;
    private Double quantity;
    private boolean deleted;
    private String imageUrl;
}
