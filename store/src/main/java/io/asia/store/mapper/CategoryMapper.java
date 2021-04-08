package io.asia.store.mapper;

import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dto.CategoryDto;
import java.util.List;

public interface CategoryMapper {
    Category toDao(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
    List<CategoryDto> allToDto(List<Category> categories);
    List<Category> allToDao(List<CategoryDto> categories);
}
