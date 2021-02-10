package io.asia.store.mapper.impl

import io.asia.store.domain.dao.Category
import io.asia.store.domain.dto.CategoryDto
import spock.lang.Specification

class CategoryMapperImplSpec extends Specification{
    def mapper = new CategoryMapperImpl();

    def 'test toDao category'(){
        given:
        CategoryDto categoryDto = new CategoryDto(1L, "a", 1L)

        when:
        def result = mapper.toDao(categoryDto)

        then:
        result != null
    }

    def 'test toDto category'(){
        given:
        Category category = new Category(1L, "a", 2L, null, null)

        when:
        def result = mapper.toDto(category)

        then:
        result != null
    }
}
