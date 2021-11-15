package com.knx.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 跨域相应头统一处理
 */
@Slf4j
@Component
public class PostFilter2 implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then( Mono.fromRunnable(()->{
            ServerHttpRequest request = exchange.getRequest();
            exchange.getResponse().getHeaders().set("post-header2", "1");
            log.debug("【POST-FILTER】【{}】PostFilter2, path:{}", this.getOrder(), request.getURI().getPath());
        }));
    }
}