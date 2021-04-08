package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.Basket;
import io.asia.store.domain.dto.BasketDto;
import io.asia.store.mapper.BasketMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapperImpl implements BasketMapper {
    @Override
    public Basket toDao(BasketDto basketDto) {
        return Basket.builder()
                .id(basketDto.getId())
                .quantity(basketDto.getQuantity())
                .product(basketDto.getProduct())
                .user(basketDto.getUser())
                .build();
    }

    @Override
    public BasketDto toDto(Basket basket) {
        return BasketDto.builder()
                .id(basket.getId())
                .quantity(basket.getQuantity())
                .product(basket.getProduct())
                .user(basket.getUser())
                .build();
    }

    @Override
    public List<BasketDto> listBasketsToDto(List<Basket> basketsList) {
        return basketsList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
