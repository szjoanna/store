package io.asia.store.service;

import io.asia.store.domain.dao.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    @CachePut(cacheNames = "product", key = "#result.id")
    Product saveProduct(Product product, Long categoryId, MultipartFile file);
    @Cacheable(cacheNames = "product", key = "#id")
    Product findById(Long id);
    @CacheEvict(cacheNames = "product", key = "#id")
    void removeById(Long id);
    Page<Product> getPage(boolean main, Pageable pageable);
    @CachePut(cacheNames = "product", key = "#result.id")
    Product update(Product product, Long id, Long categoryId);
    List<Product> findByMain(boolean isMain);
    List<String> autoComplete(String value);
}
