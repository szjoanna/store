package io.asia.store.service.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.repository.CategoryRepository;
import io.asia.store.repository.ProductRepository;
import io.asia.store.service.CategoryService;
import io.asia.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
//@Scope("prototype") - uzywamy gdy nasze globalne zmienne w klasie beda sie zmieniac (kazdy watek bedzie mial
//swoje wlasne wartosci w klasie)
//tworzy konstruktor dla finalnych zmiennych
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public Product saveProduct(Product product, Long categoryId) {
        product.setCategory(categoryService.getById(categoryId));
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        log.info("Product not in cache {}", id);
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with " + id + " does not exist"));
    }

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getPage(boolean main, Pageable pageable) {
        return productRepository.findByMain(main, pageable);
    }

    @Override
    public Product update(Product product, Long id, Long categoryId) {
        Product productDB = findById(id);
        productDB.setCategory(categoryService.getById(categoryId));
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        productDB.setVersion(product.getVersion());
        return productRepository.save(productDB);
    }
}
