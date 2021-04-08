package io.asia.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasketDto {
    private Long id;
    private Double quantity;
    private Product product;
    private User user;
}
