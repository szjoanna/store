package io.asia.store.service.impl;

import io.asia.store.domain.dao.Basket;
import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.User;
import io.asia.store.repository.BasketRepository;
import io.asia.store.service.BasketService;
import io.asia.store.service.ProductService;
import io.asia.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final UserService userService;
    private final BasketRepository basketRepository;
    private final ProductService productService;

    @Override
    public List<Basket> getBasketByCurrentUser() {
        return basketRepository.findByUserId(userService.getCurrentUser().getId());
    }

    @Override
    public Basket addProductToBasket(Long idProduct, Double quantity) {
        User user = userService.getCurrentUser();
        Product product = productService.findById(idProduct);

        Basket basketDB = getBasketByCurrentUser().stream()
                .filter(basket -> basket.getProduct().getId().equals(idProduct))
                .findAny()
                .orElse(null);

        if(basketDB != null) {
            Double totalQuantity = basketDB.getQuantity() + quantity;
            if(totalQuantity <= basketDB.getProduct().getQuantity()) {
                basketDB.setQuantity(totalQuantity);
                return basketRepository.save(basketDB);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            if(quantity <= product.getQuantity()) {
                Basket basket = Basket.builder()
                        .product(product)
                        .user(user)
                        .quantity(quantity)
                        .build();
                return basketRepository.save(basket);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    @Transactional
    public void deleteProductFromBasket(Long idProduct) {
        basketRepository.deleteByProductId(idProduct);
    }

    @Override
    public void deleteAllCurrentUserProducts() {
        basketRepository.deleteAll();
    }

    @Override
    public Basket updateProductFromBasket(Long idProduct, Double quantity) {
        Basket basketDB = getBasketByCurrentUser().stream()
                .filter(basket -> basket.getProduct().getId().equals(idProduct))
                .findAny()
                .orElse(null);

        if( basketDB != null && quantity <= basketDB.getProduct().getQuantity()) {
            basketDB.setQuantity(quantity);
            return basketRepository.save(basketDB);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
