package io.asia.store.repository;

import io.asia.store.domain.dao.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUserId(Long userId);
    void deleteByProductId(Long productId);
}
