package com.knx.gateway.config.swagger;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Primary
@Component
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v3/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition))));
        return resources;
    }

    private SwaggerResource swaggerResource(RouteDefinition routeDefinition) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(routeDefinition.getId());
        swaggerResource.setSwaggerVersion(DocumentationType.OAS_30.getVersion());
        fillSwaggerResourcesLocation(swaggerResource, routeDefinition);
        return swaggerResource;
    }

    private static void fillSwaggerResourcesLocation(SwaggerResource swaggerResource, RouteDefinition routeDefinition) {
        List<PredicateDefinition> predicates = routeDefinition.getPredicates();
        PredicateDefinition predicateDefinition = predicates.stream().findFirst().get();
        Map<String, String> args = predicateDefinition.getArgs();
        Collection<String> values = args.values();
        String predicateUrl = values.stream().findFirst().get();
        String substring = StringUtils.substring(predicateUrl, 0, StringUtils.indexOf(predicateUrl, "/**"));
        swaggerResource.setLocation(substring + API_URI);
    }
}
