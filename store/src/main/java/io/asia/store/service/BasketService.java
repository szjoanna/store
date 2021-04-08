package io.asia.store.service;

import io.asia.store.domain.dao.Basket;

import java.util.List;

public interface BasketService {
    List<Basket> getBasketByCurrentUser();
    Basket addProductToBasket(Long idProduct, Double quantity);
    void deleteProductFromBasket(Long idProduct);
    Basket updateProductFromBasket(Long idProduct, Double quantity);
    void deleteAllCurrentUserProducts();
}
