package io.asia.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.asia.store.validator.PasswordValid;
import io.asia.store.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@PasswordValid(groups = Create.class)
public class UserDto extends AuditableDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    @NotBlank
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", groups = Create.class)
    private String password;
    private String confirmPassword;
    private List<String> roles;
    private Integer revisionNumber;
}
