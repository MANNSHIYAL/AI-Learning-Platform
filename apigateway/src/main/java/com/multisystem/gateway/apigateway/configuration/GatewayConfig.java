package com.multisystem.gateway.apigateway.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.PrincipalNameKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multisystem.gateway.apigateway.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                // ── AUTH SERVICE ──────────────────────────────────────────────
                // Public routes — NO JWT filter, just rate limiting
                .route("auth-service-public", r -> r
                    .path("/auth/register","/auth/login")
                    .filters(f -> f
                        .requestRateLimiter(config -> config
                            .setRateLimiter(redisRateLimiter(5, 10))
                            .setKeyResolver(new PrincipalNameKeyResolver())
                        )
                        .addResponseHeader("X-Gateway", "AI-Learning-Platform")
                    )
                    .uri("lb://auth-service")
                )
                // Auth validate — called by other services, needs JWT
                .route("auth-service-secure", r -> r
                    .path("/auth/validate","/auth/user/**")
                    .filters(f -> f
                    .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))
                        .requestRateLimiter(config -> config
                            .setRateLimiter(redisRateLimiter(20, 30))
                        )
                    )
                    .uri("lb://auth-service")
                )
                .build();
    }
    private RedisRateLimiter redisRateLimiter(int replenishRate, int burstCapacity) {
        return new RedisRateLimiter(replenishRate, burstCapacity);
    }
}
