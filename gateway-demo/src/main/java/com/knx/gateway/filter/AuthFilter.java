package com.knx.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.knx.common.base.exception.code.ErrorCode;
import com.knx.common.base.model.WebResponse;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();
        log.debug("【PRE-FILTER】【{}】AuthFilter, path:{}", this.getOrder(), path);
        if(StringUtils.contains(path, "/401")) {
            return fastFail1(exchange.getResponse(), ErrorCode.UNAUTHORIZED);
        } else if(StringUtils.contains(path, "/403")) {
            return fastFail2(exchange.getResponse(), ErrorCode.FORBIDDEN);
        } else if(StringUtils.contains(path, "/404")) {
            return fastFail3(exchange.getResponse(), ErrorCode.NOT_FOUND);
        }
        return chain.filter(exchange);
    }


    private Mono<Void> fastFail1(ServerHttpResponse response, ErrorCode errorCode){
        response.setStatusCode(HttpStatus.resolve(errorCode.getStatus()));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = JSON.toJSONString(WebResponse.returnFail(errorCode)).getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }


    private Mono<Void> fastFail2(ServerHttpResponse response, ErrorCode errorCode){
        response.setStatusCode(HttpStatus.resolve(errorCode.getStatus()));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = JSON.toJSONString(WebResponse.returnFail(errorCode)).getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = response.bufferFactory().allocateBuffer(bytes.length).write(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }


    private Mono<Void> fastFail3(ServerHttpResponse response, ErrorCode errorCode){
        response.setStatusCode(HttpStatus.resolve(errorCode.getStatus()));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        WebResponse<?> responseObj = WebResponse.returnFail(errorCode);
        byte[] bytes = JSON.toJSONString(responseObj).getBytes(StandardCharsets.UTF_8);
        return response.writeAndFlushWith(
                Mono.just(ByteBufMono.just(response.bufferFactory().wrap(bytes))));

    }



    @Override
    public int getOrder() {
        return 2;
    }
}
