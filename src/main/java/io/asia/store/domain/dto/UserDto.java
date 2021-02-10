package io.asia.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Data
//bezargumentowy konstruktor
@NoArgsConstructor
//konstruktor z wszytskich argumentów
@AllArgsConstructor
//do JSON nie będą przekazywane puste wartosci
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private List<String> roles;
}
