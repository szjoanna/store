package io.asia.store.service.impl;

import io.asia.store.domain.dao.Basket;
import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.UserOrder;
import io.asia.store.repository.UserOrderRepository;
import io.asia.store.service.ProductService;
import io.asia.store.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderServiceImpl implements UserOrderService {
    private final UserOrderRepository userOrderRepository;
    private final BasketServiceImpl basketService;
    private final UserServiceImpl userService;
    private final ProductService productService;

    @Override
    public void createOrder() {
        List<Basket> baskets = basketService.getBasketByCurrentUser();
        baskets.stream()
                .filter(basket -> basket.getQuantity() <= basket.getProduct().getQuantity())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Basket quantity should be updated"));
        String orderNumber = UUID.randomUUID().toString();
        UserOrder userOrder = null;
        List<UserOrder> userOrders = baskets.stream()
                .map(basket -> UserOrder.builder()
                        .user(basket.getUser())
                        .product(basket.getProduct())
                        .quantity(basket.getQuantity())
                        .orderNumber(orderNumber)
                        .build())
                .collect(Collectors.toList());
        userOrderRepository.saveAll(userOrders);
        for (Basket basket : baskets) {
            Product product = basket.getProduct();
            product.setQuantity(basket.getProduct().getQuantity() - basket.getQuantity());
            productService.update(product, basket.getProduct().getId(), basket.getProduct().getCategory().getId());
        }
        basketService.deleteAllCurrentUserProducts();
    }

    @Override
    public List<UserOrderRepository.Order> getOrders() {
        return userOrderRepository.findOrders(userService.getCurrentUser().getId());
    }

    @Override
    public Page<UserOrder> getOrderDetails(String orderNumber, Pageable pageable) {
        return userOrderRepository.findByOrderNumber(orderNumber, pageable);
    }
}
