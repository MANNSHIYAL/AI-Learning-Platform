package com.multisystem.gateway.apigateway.filter;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.multisystem.gateway.apigateway.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config>{
    private final JwtUtil jwtUtil; 
    private static final String BEARER = "Bearer ";

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest()
                                .getHeaders()
                                .getFirst(HttpHeaders.AUTHORIZATION);
            if(Optional.ofNullable(authHeader).filter(h -> h.startsWith(BEARER)).isEmpty()){
                return unauthorized(exchange);
            }

            String token = authHeader.substring(BEARER.length());

            return Optional.of(token)
                    .filter(jwtUtil::isTokenValid)
                    .map(validToken -> {
                        String userId = jwtUtil.extractUserId(validToken);
                        String role = jwtUtil.extractRole(validToken);

                        ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(r -> r
                                .header("X-User-Id", userId)
                                .header("X-User-Role", role)
                            ).build();
                        return chain.filter(mutatedExchange);
                    })
                    .orElseGet(() -> unauthorized(exchange));
        };
    }
    public static class Config {
        // empty for now, add per-route options later (e.g. required roles)
    }

}
