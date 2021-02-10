package io.asia.store.service.impl;

import io.asia.store.domain.dao.Category;
import io.asia.store.repository.CategoryRepository;
import io.asia.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with " + id + " does not exist"));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void removeById(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Category update(Category category, Long id) {
        Category categoryDb = getById(id);
        categoryDb.setName(category.getName());
        categoryDb.setVersion(category.getVersion());
        return saveCategory(categoryDb);
    }

    @Override
    public Page<Category> getPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
