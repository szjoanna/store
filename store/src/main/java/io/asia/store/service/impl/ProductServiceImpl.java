package io.asia.store.service.impl;

import io.asia.store.domain.dao.Product;
import io.asia.store.repository.ProductRepository;
import io.asia.store.service.CategoryService;
import io.asia.store.service.ProductService;
import io.asia.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    @Value("${product.image}")
    private String imagePath;

    @Override
    public Product saveProduct(Product product, Long categoryId, MultipartFile file) {
        product.setCategory(categoryService.getById(categoryId));
        product.setImageUrl(product.getName() + ".png");
        Product productSave = productRepository.save(product);
        try {
            Files.copy(file.getInputStream(), Paths.get(imagePath, productSave.getImageUrl()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.warn("File couldn't save", e);
        }
        return productSave;
    }

    @Override
    public Product findById(Long id) {
        log.info("Product not in cache {}", id);
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with " + id + " does not exist"));
    }

    @Override
    public void removeById(Long id) {
        Product product = findById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Page<Product> getPage(boolean main, Pageable pageable) {
        try {
            if (userService.getCurrentUser().getRoles().removeIf(role -> "ROLE_ADMIN".equals(role.getName()))) {
                return productRepository.findAll(pageable);
            }
        } catch (EntityNotFoundException e) {
            log.debug(e.getMessage());
        }
        return productRepository.findByMainAndDeleted(main, false, pageable);
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

    @Override
    public List<Product> findByMain(boolean isMain) {
        return productRepository.findByMainAndDeleted(isMain, false);
    }

    @Override
    public List<String> autoComplete(String value) {
        return productRepository.autoComplete(value);
    }
}
