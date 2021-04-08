package io.asia.store.controller;

import io.asia.store.domain.dto.CategoryDto;
import io.asia.store.mapper.CategoryMapper;
import io.asia.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{id}")
    CategoryDto findById(@PathVariable Long id) {
        return categoryMapper.toDto(categoryService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    CategoryDto saveCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryService.saveCategory(categoryMapper.toDao(categoryDto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto, @PathVariable Long id) {
        return categoryMapper.toDto(categoryService.update(categoryMapper.toDao(categoryDto), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
    }

    @GetMapping
    public Page<CategoryDto> getPageCategory(@RequestParam int page, @RequestParam int size) {
        return categoryService.getPage(PageRequest.of(page, size)).map(categoryMapper:: toDto);
    }

    @GetMapping("/all")
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.allToDto(categoryService.findAll());
    }
}
