package com.knx.gateway.filter;

import com.knx.gateway.config.swagger.SwaggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Felix Zhang
 * @Package com.knx.gateway
 * @date 2021/2/3 14:00
 */
@Slf4j
@Component("swaggerSubPrefixURLFilter")
public class SwaggerSubPrefixURLFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        if (StringUtils.contains(request.getURI().getPath(), SwaggerProvider.API_URI)) {
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, request.getURI());
            String path = request.getURI().getRawPath();
            String newPath = SwaggerProvider.API_URI;
            newPath = newPath + (newPath.length() > 1 && path.endsWith("/") ? "/" : "");
            ServerHttpRequest newRequest = request.mutate().path(newPath).build();
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
            return chain.filter(exchange.mutate().request(newRequest).build());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER - 1;
    }
}
