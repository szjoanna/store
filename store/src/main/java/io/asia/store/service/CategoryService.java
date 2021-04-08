package io.asia.store.service;

import io.asia.store.domain.dao.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {
    Category getById(Long id);
    Category saveCategory(Category category);
    void removeById(Long id);
    Category update(Category category, Long id);
    Page<Category> getPage(Pageable pageable);
    List<Category> findAll();
}
