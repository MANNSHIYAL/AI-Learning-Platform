package com.multisystem.auth.authservice.filters;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.multisystem.auth.authservice.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);

        if(header == null || !header.startsWith(BEARER)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(BEARER.length());

        if(jwtService.isTokenValid(token)){
            String email = jwtService.extractEmail(token);

            UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(email,null,Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
