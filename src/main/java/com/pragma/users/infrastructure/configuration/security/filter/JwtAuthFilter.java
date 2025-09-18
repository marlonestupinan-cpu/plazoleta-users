package com.pragma.users.infrastructure.configuration.security.filter;

import com.pragma.users.application.handler.impl.auth.jwt.JwtGenerator;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.api.auth.ITokenServicePort;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.auth.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final ITokenServicePort tokenService;
    private final JwtGenerator jwtGenerator;
    private final UserDetailsService userDetailsService;
    private final @Lazy IUserServicePort userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authToken = authHeader.substring(7);
        final String email = jwtGenerator.extractUsername(authToken);

        if (email == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Token token = tokenService.findByToken(authToken);
        if (token == null || token.isExpired() || token.isRevoked()) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (UsernameNotFoundException e) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isTokenValid = jwtGenerator.validateToken(authToken, user);

        if (!isTokenValid) {
            filterChain.doFilter(request, response);
            return;
        }
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
