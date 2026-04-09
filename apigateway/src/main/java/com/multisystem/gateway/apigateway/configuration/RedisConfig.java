package com.multisystem.gateway.apigateway.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class RedisConfig {
    @Bean
    public KeyResolver ipKeyResolver(){
        return exchange -> {
            String ip = exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostName();
            return Mono.just(ip);
        };
    }
    // @Bean
    // public KeyResolver userKeyResolver() {
    //     return exchange -> Mono.justOrEmpty(
    //         exchange.getRequest().getHeaders().getFirst("X-User-Id")
    //     ).defaultIfEmpty("anonymous");
    // }
}
