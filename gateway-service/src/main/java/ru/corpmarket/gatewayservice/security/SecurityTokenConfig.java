package ru.corpmarket.gatewayservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityTokenConfig {

    private final JwtConfig jwtConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((httpServletRequest, httpServletResponse, e) ->
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, jwtConfig.getAuthUri()).permitAll()
                        .requestMatchers(HttpMethod.POST, "/consumers/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/consumers/**").hasAnyRole("CONSUMER", "ADMIN")
                        .requestMatchers("/consumers/**").hasAnyRole("CONSUMER", "ADMIN")
                        .requestMatchers("/products/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers("/carts/**").hasAnyRole("CONSUMER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("CONSUMER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/orders/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/consumers/**").hasAnyRole("CONSUMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/orders/**").hasAnyRole("CONSUMER", "ADMIN")
                        .anyRequest().authenticated());
        return http.build();
    }
}