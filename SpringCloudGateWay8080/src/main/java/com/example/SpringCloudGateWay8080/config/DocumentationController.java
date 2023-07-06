package com.example.SpringCloudGateWay8080.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("Client-Main-GateWay", "/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Client-Service-StatusAndMess8085", "/api/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Client-Service-SpringBatch8084", "/batch/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Client-Service-UploadFile8082", "/upload/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Client-Service-UserSpringJPA8081", "/user/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
