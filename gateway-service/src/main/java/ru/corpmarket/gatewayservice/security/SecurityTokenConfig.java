package ru.corpmarket.gatewayservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
               .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers("/admins/**").permitAll()
                .antMatchers("/admins/**").permitAll()
                .antMatchers("/managers/**").permitAll()
                .antMatchers(HttpMethod.POST, "/consumers/").permitAll()
                .antMatchers(HttpMethod.GET, "/consumers/all").permitAll()
                .antMatchers(HttpMethod.GET, "/consumers/**").permitAll()//.hasAnyRole("CONSUMER", "ADMIN")
                .antMatchers("/consumers/**").permitAll()//hasAnyRole("CONSUMER", "ADMIN")
                .antMatchers("/products/catalog").permitAll()
                .antMatchers(HttpMethod.POST, "/products/**").permitAll()//.hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/**").permitAll()//.hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .antMatchers(HttpMethod.GET, "/carts/ping").permitAll()
                .antMatchers("/carts/**").permitAll()//.hasAnyRole("CONSUMER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders/**").permitAll()//hasAnyRole("CONSUMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/consumers/**").permitAll()//hasAnyRole("CONSUMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/products/**").permitAll()//hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/orders/**").permitAll()//hasAnyRole("CONSUMER", "ADMIN")
                .anyRequest().permitAll();
    }
}
