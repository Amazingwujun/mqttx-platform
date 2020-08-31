package com.jun.mqttxplatform.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.jun.mqttxplatform.security.JWTAuthenticationEntryPoint;
import com.jun.mqttxplatform.security.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMIT_ROUTES = {
            "/user/signIn", "/user/signUp"
    };

    private BizConfig bizConfig;

    public SecurityConfig(BizConfig bizConfig) {
        this.bizConfig = bizConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Algorithm algorithm() {
        String jwtKey = bizConfig.getJwtKey();
        return Algorithm.HMAC256(jwtKey);
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(algorithm())
                .withIssuer("mqttx")
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf().disable()
                .rememberMe().disable()
                .authorizeRequests()
                .antMatchers(PERMIT_ROUTES).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JWTAuthenticationFilter(jwtVerifier(), new JWTAuthenticationEntryPoint()), LogoutFilter.class);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
