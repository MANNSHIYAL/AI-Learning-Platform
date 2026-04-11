package com.multisystem.gateway.apigateway.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
                        .requestRateLimiter(config -> config
                            .setRateLimiter(unsecureAuthRateLimiter())
                            // .setKeyResolver(new PrincipalNameKeyResolver())
                            .setKeyResolver(redisConfig.ipKeyResolver())
                        )
                        .addResponseHeader("X-Gateway", "AI-Learning-Platform")
                    )
                    .uri("lb://auth-service")
                )
                // Auth validate — called by other services, needs JWT
                .route("auth-service-secure", r -> r
                    .path("/auth/validate","/auth/test","/auth/user/**")
                    .filters(f -> f
                    .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))
                        .requestRateLimiter(config -> config
                            .setRateLimiter(secureAuthRateLimiter())
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
                            .setRateLimiter(taskRateLimiter())
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
                            .setRateLimiter(aiRateLimiter())
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
                            .setRateLimiter(embeddingRateLimiter())
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
    // private RedisRateLimiter redisRateLimiter(int replenishRate, int burstCapacity) {
    //     return new RedisRateLimiter(replenishRate, burstCapacity);
    // }
    @Bean
    @Primary
    public RedisRateLimiter unsecureAuthRateLimiter() {
        return new RedisRateLimiter(5, 10);
    }
    @Bean
    public RedisRateLimiter secureAuthRateLimiter() {
        return new RedisRateLimiter(20, 30);
    }

    @Bean
    public RedisRateLimiter taskRateLimiter() {
        return new RedisRateLimiter(30, 50);
    }
    @Bean
    public RedisRateLimiter aiRateLimiter() {
        return new RedisRateLimiter(30, 50);
    }
    @Bean
    public RedisRateLimiter embeddingRateLimiter() {
        return new RedisRateLimiter(30, 50);
    }
}
