package io.asia.store.config;

import com.google.common.collect.Multimap;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//nadpisujemy beana, stworzonego wczesniej przez swager
@Primary
@Configuration
public class SwaggerLoginConfig extends ApiListingScanner {

    public SwaggerLoginConfig(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
        super(apiDescriptionReader, apiModelReader, pluginsManager);
    }

    @Override
    public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {
        Multimap<String, ApiListing> scan = super.scan(context);
        List<Operation> operations = new ArrayList<>();
        operations.add(new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .uniqueId("login")
                .parameters(Collections.singletonList(new ParameterBuilder()
                        .name("body")
                        .required(true)
                        .description("body for login request")
                        .parameterType("body")
                        .modelRef(new ModelRef("LoginDto"))
                        .build()))
                .responseMessages(Collections.singleton(new ResponseMessage(200, "correct response", new ModelRef("string"), Collections.emptyMap(), Collections.emptyList())))
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .responseModel(new ModelRef("string"))
                .summary("login")
                .notes("here you can login")
                .build()
        );
        List<ApiDescription> apis = new ArrayList<>();
        apis.add(new ApiDescription("login", "/api/login", "login", operations, false));
        scan.put("authentication", new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
                .apis(apis)
                .description("authentication")
                .build());
        return scan;
    }
}
