package io.asia.store.mapper.impl

import io.asia.store.domain.dao.Category
import io.asia.store.domain.dto.CategoryDto
import io.asia.store.mapper.CategoryMapperImpl
import spock.lang.Specification

class CategoryMapperImplSpec extends Specification {
    def mapper = new CategoryMapperImpl();

    def 'test toDao category'() {
        given:
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("a")
                .version(1L)
                .build()

        when:
        def result = mapper.toDao(categoryDto)

        then:
        result.name.equals(categoryDto.name)
        result.version.equals(categoryDto.version)
    }

    def 'test toDto category'() {
        given:
        Category category = Category.builder()
                .id(1L)
                .name("a")
                .version(2L)
                .createdDate(null)
                .lastModifiedDate(null)
                .build()

        when:
        def result = mapper.toDto(category)

        then:
        result.name.equals(category.name)
        result.version.equals(category.version)
    }

    def 'test toDao listOfCategory'() {
        given:
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("a")
                .version(1L)
                .build()
        CategoryDto categoryDto2 = CategoryDto.builder()
                .id(2L)
                .name("b")
                .version(2L)
                .build()
        List<CategoryDto> categories = Arrays.asList(categoryDto, categoryDto2)

        when:
        def result = mapper.allToDao(categories)

        then:
        result.get(0).name.equals(categoryDto.name)
        result.get(0).version.equals(categoryDto.version)
        result.get(1).name.equals(categoryDto2.name)
        result.get(1).version.equals(categoryDto2.version)
    }

    def 'test toDto listOfCategory'() {
        given:
        Category category = Category.builder()
                .id(1L)
                .name("a")
                .version(1L)
                .build()
        Category category2 = Category.builder()
                .id(2L)
                .name("b")
                .version(2L)
                .build()
        List<Category> categories = Arrays.asList(category, category2)

        when:
        def result = mapper.allToDto(categories)

        then:
        result.get(0).name.equals(category.name)
        result.get(0).version.equals(category.version)
        result.get(1).name.equals(category2.name)
        result.get(1).version.equals(category2.version)
    }
}
