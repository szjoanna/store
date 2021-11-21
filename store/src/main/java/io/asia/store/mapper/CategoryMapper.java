package io.asia.store.mapper;

import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CategoryMapper extends BasicMapper<CategoryDto, Category> {
    Category toDao(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
    List<CategoryDto> allToDto(List<Category> categories);
    List<Category> allToDao(List<CategoryDto> categories);
}
