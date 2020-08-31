package com.jun.mqttxplatform.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jun.mqttxplatform.entity.po.Permission;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用 JWT 对用户的权限进行校验
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_KEY = "x-token";

    private JWTVerifier jwtVerifier;

    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public JWTAuthenticationFilter(JWTVerifier jwtVerifier, JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtVerifier = jwtVerifier;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Integer userId = verify.getClaim("userId").asInt();
            List<Permission> permissions = verify.getClaim("permissions").asList(Permission.class);

            UsernamePasswordAuthenticationToken authenticationToken;
            if (CollectionUtils.isEmpty(permissions)) {
                authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, null);
            } else {
                List<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                        .collect(Collectors.toList());
                authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, grantedAuthorities);
            }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();

            this.jwtAuthenticationEntryPoint.commence(request, response, new BadCredentialsException("令牌校验失败", e));
            return;
        }

        chain.doFilter(request, response);
    }
}
