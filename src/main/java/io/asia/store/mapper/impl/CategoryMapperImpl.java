package io.asia.store.mapper.impl;

import io.asia.store.domain.dao.Category;
import io.asia.store.domain.dto.CategoryDto;
import io.asia.store.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public Category toDao(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .version(categoryDto.getVersion())
                .build();
    }

    @Override
    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .version(category.getVersion())
                .build();
    }

    @Override
    public List<CategoryDto> allToDto(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> allToDao(List<CategoryDto> categories) {
        return categories.stream()
                .map(this::toDao)
                .collect(Collectors.toList());
    }
}
