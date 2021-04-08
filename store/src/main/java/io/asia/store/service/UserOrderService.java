package io.asia.store.service;

import io.asia.store.domain.dao.UserOrder;
import io.asia.store.repository.UserOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserOrderService {
    void createOrder();
    List<UserOrderRepository.Order> getOrders();
    Page<UserOrder> getOrderDetails(String orderNumber, Pageable pageable);
}
