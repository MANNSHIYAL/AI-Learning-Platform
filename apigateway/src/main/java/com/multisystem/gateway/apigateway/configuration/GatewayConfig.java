package com.multisystem.gateway.apigateway.configuration;

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
    private final RedisConfig redisConfig;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                // ── AUTH SERVICE ──────────────────────────────────────────────
                // Public routes — NO JWT filter, just rate limiting
                .route("auth-service-public", r -> r
                    .path("/auth/register","/auth/login")
                    .filters(f -> f
                        // .requestRateLimiter(config -> config
                        //     .setRateLimiter(redisRateLimiter(5, 10))
                        //     // .setKeyResolver(new PrincipalNameKeyResolver())
                        //     .setKeyResolver(redisConfig.ipKeyResolver())
                        // )
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
                // ── TASK SERVICE ──────────────────────────────────────────────
                // All task routes require valid JWT
                .route("tast-service",r -> r
                    .path("/task/**")
                    .filters(f ->f
                        .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))
                        .requestRateLimiter(config -> config
                            .setRateLimiter(redisRateLimiter(30, 50))
                        )
                        // Incase if I am using api/tasks/{something} => Then this will rewrite the api for the load balancer to redirect it to the correct service.
                        // .rewritePath("/tasks/(?<segment>.*)", "/api/tasks/${segment}")
                    )
                    .uri("lb://task-service")
                )
                // ── AI SERVICE ────────────────────────────────────────────────
                // AI calls are expensive — stricter rate limit (10 req/sec)
                .route("ai-service",r -> r
                    .path("/ai-service/**")
                    .filters(f ->f 
                        .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))
                        .requestRateLimiter(config -> config
                            .setRateLimiter(redisRateLimiter(10, 15))
                        )
                    )
                    .uri("lb://ai-service")
                )
                // ── EMBEDDING SERVICE ─────────────────────────────────────────
                .route("embedding-service",r -> r
                    .path("/embedding-service/**")
                    .filters(f -> f
                        .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))
                        .requestRateLimiter(config -> config
                            .setRateLimiter(redisRateLimiter(20, 30))
                        )
                    )
                    .uri("lb://embedding-service")
                )
                // ── ACTUATOR (health check, no auth) ─────────────────────────
                .route("gateway-autuator", r -> r
                    .path("/gateway-actuator/**")
                    .uri("http://localhost:8080")
                )
                .build();
    }
    private RedisRateLimiter redisRateLimiter(int replenishRate, int burstCapacity) {
        return new RedisRateLimiter(replenishRate, burstCapacity);
    }
}
