package io.asia.store.repository;

import io.asia.store.domain.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByMainAndDeleted(boolean main, boolean deleted, Pageable pageable);
    List<Product> findByMainAndDeleted(boolean isMain, boolean deleted);
    @Query(value = "SELECT name FROM product WHERE name like %?1% AND deleted = false", nativeQuery = true)
    List<String> autoComplete(String value);
}
