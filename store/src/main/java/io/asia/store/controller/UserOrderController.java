package io.asia.store.controller;

import io.asia.store.domain.dto.ProductDto;
import io.asia.store.mapper.UserOrderMapper;
import io.asia.store.repository.UserOrderRepository;
import io.asia.store.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class UserOrderController {
    private final UserOrderService userOrderService;
    private final UserOrderMapper userOrderMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void createOrder() {
        userOrderService.createOrder();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<UserOrderRepository.Order> getOrders() {
        return userOrderService.getOrders();
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public Page<ProductDto> getOrderDetails(@RequestParam String orderNumber, @RequestParam int page, @RequestParam int size) {
        return userOrderService.getOrderDetails(orderNumber, PageRequest.of(page, size))
                .map(userOrderMapper::userOrderToDto);
    }
}
