package io.asia.store.mapper;

import io.asia.store.domain.dao.UserOrder;
import io.asia.store.domain.dto.ProductDto;

import java.util.List;

public interface UserOrderMapper {
    ProductDto userOrderToDto(UserOrder userOrder);
}
