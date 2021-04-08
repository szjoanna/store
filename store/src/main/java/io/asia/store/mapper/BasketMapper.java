package io.asia.store.mapper;

import io.asia.store.domain.dao.Basket;
import io.asia.store.domain.dto.BasketDto;

import java.util.List;

public interface BasketMapper {
    Basket toDao(BasketDto basketDto);
    BasketDto toDto(Basket basket);
    List<BasketDto> listBasketsToDto(List<Basket> basketsList);
}
