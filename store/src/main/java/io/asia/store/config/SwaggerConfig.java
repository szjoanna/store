package io.asia.store.config;

import com.fasterxml.classmate.TypeResolver;
import io.asia.store.domain.dto.LoginDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    Docket docket(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securityScheme()))
                .additionalModels(typeResolver.resolve(LoginDto.class))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("store", "store application", "0.0.1-SNAPSHOT", "urn:tos",
                new Contact("asia", "www.github", "szelagasia@gmail.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }

    private SecurityScheme securityScheme() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope scope = new AuthorizationScope("global", "global authorization scope");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        return Collections.singletonList(new SecurityReference("JWT", scopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(path -> false)
                .build();
    }
}
