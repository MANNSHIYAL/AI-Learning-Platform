package com.multisystem.gateway.apigateway.filter;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class RequestLoggingFilter implements GlobalFilter,Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;

            int statusCode = Optional.ofNullable(exchange.getResponse().getStatusCode())
            .map(HttpStatusCode::value)
            .orElse(0);

            log.info("[GATEWAY] {} {} → {} ({}ms)", method, path, statusCode, duration);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
