package io.surisoft.sample.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.springdoc.core.utils.Constants.ALL_PATTERN;

@Configuration
public class SwaggerConfig {

    @Value("${server.port}")
    private int port;

    @Value("${host}")
    private String host;

    @Bean
    public OpenAPI generateOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        Server server = new Server();
        server.setUrl(host + port);
        openAPI.servers(List.of(server));
        openAPI.info(new Info().title("Open API Sample Service")
                .description("Sample Service")
                .version("0.0.1")
                .license(new License().name("Apache 2.0")));
        return openAPI;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("sample-services")
                .packagesToScan("io.surisoft.sample.controller")
                .build();
    }

    /*@Bean
    public GroupedOpenApi actuatorApi(OpenApiCustomizer actuatorOpenApiCustomizer,
                                      //OperationCustomizer actuatorCustomizer,
                                      WebEndpointProperties endpointProperties) {
        return GroupedOpenApi.builder()
                .group("Actuator")
                .pathsToMatch(endpointProperties.getBasePath() + ALL_PATTERN)
                .addOpenApiCustomizer(actuatorOpenApiCustomizer)
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Actuator API").version("1")))
                .group("sample-services")
                .packagesToScan("io.surisoft.sample.controller")
                .build();
    }*/
}