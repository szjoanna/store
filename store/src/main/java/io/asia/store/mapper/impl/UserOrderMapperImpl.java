package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.UserOrder;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.ProductMapper;
import io.asia.store.mapper.UserOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserOrderMapperImpl implements UserOrderMapper {
    private final ProductMapper productMapper;

    @Override
    public ProductDto userOrderToDto(UserOrder userOrder) {
        ProductDto product = productMapper.toDto(userOrder.getProduct());
        product.setQuantity(userOrder.getQuantity());
        return product;
    }
}
