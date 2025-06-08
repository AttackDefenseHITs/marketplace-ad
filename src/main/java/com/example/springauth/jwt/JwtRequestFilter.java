package com.example.springauth.jwt;

import com.example.springauth.entity.User;
import com.example.springauth.repository.RedisRepository;
import com.example.springauth.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            boolean isBanned = redisRepository.has(jwtTokenUtils.getIdFromToken(jwt));
            if (isBanned) {
                log.info("Attempt to use a banned token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is banned");
                return;
            }
            try {
                UUID userId = jwtTokenUtils.getUserIdFromToken(jwt);
                String role = jwtTokenUtils.getAllClaimsFromToken(jwt).get("role", String.class);
                if (userId != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userRepository.findById(userId).orElse(null);
                    if (user != null) {
                        List<GrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + role));

                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                user, jwt, authorities
                        );
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }
            } catch (ExpiredJwtException e) {
                log.debug("Token is expired :(");
            } catch (Exception e) {
                log.error("Error processing JWT", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
