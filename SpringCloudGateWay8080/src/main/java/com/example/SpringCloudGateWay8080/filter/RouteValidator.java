package com.example.SpringCloudGateWay8080.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/user/signIn",
            "/user/register",
            "/api/v2/api-docs",
            "/batch/v2/api-docs",
            "/upload/v2/api-docs",
            "/user/v2/api-docs",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
