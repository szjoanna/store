package io.asia.store.mapper;

import io.asia.store.domain.dao.Product;
import io.asia.store.domain.dao.Role;
import io.asia.store.domain.dao.User;
import io.asia.store.domain.dto.ProductDto;
import io.asia.store.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.history.Revision;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.secondName", target = "secondName")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "entity.password", target = "password")
    @Mapping(source = "entity.roles", target = "roles", qualifiedByName = "rolesToNames")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto toUserDto(Revision<Integer, User> revision);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.version", target = "version")
    @Mapping(source = "entity.main", target = "main")
    @Mapping(source = "entity.category.id", target = "categoryId")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "entity.deleted", target = "deleted")
    @Mapping(source = "entity.imageUrl", target = "imageUrl")
    @Mapping(source = "entity.listOfProducts", target = "subProducts", qualifiedByName = "subProducts")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    ProductDto toProductDto(Revision<Integer, Product> revision);

    @Named("rolesToNames")
    default List<String> rolesToNames(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @Named("subProducts")
    default List<ProductDto> subProducts(List<Product> products) {
        if (products == null)
            return null;
        return products.stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .version(product.getVersion())
                        .main(product.isMain())
                        .categoryId(product.getCategory().getId())
                        .quantity(product.getQuantity())
                        .deleted(product.isDeleted())
                        .imageUrl(product.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
