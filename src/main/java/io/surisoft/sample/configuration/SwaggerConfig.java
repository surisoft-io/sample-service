package io.surisoft.sample.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
}