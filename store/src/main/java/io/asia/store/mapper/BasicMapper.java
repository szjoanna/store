package io.asia.store.mapper;

import io.asia.store.domain.dao.Auditable;
import io.asia.store.domain.dto.AuditableDto;
import io.asia.store.security.SecurityUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface BasicMapper<DTO extends AuditableDto, DAO extends Auditable> {
    @AfterMapping
    default void mapFieldsForAdmin(DAO dao, @MappingTarget DTO.AuditableDtoBuilder<?, ?> dto){
        if(!SecurityUtils.hasRole("ROLE_ADMIN")) {
            dto.createdBy(null);
            dto.lastModifiedBy(null);
            dto.lastModifiedDate(null);
            dto.createdDate(null);
        }
    }
}
