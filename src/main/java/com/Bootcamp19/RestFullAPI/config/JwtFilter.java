package com.Bootcamp19.RestFullAPI.config;

import com.Bootcamp19.RestFullAPI.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/v1") || path.startsWith("/v1/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            reject(response, "Token wajib diisi");
            return;
        }

        String token = header.substring(7);
        try {
            String username = jwtUtil.extractUsername(token);
            log.info("Request username : {}", username);
        } catch (JwtException | IllegalArgumentException exception) {
            reject(response, "Token tidak valid atau sudah kadaluwarsa");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void reject(HttpServletResponse httpServletResponse, String message) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(
                "{\"status\":\"failed\", \"message\":\"" + message + "\", \"data\":null}"
        );
    }
}
