package ru.corpmarket.gatewayservice.security;
/*
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




@Slf4j
@Component
@RequiredArgsConstructor
*/
public class JwtTokenAuthenticationFilter{}/*extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull javax.servlet.http.HttpServletResponse httpServletResponse,
                                    @NonNull javax.servlet.FilterChain filterChain) throws ServletException, IOException{

        String bearerToken = httpServletRequest.getHeader(jwtConfig.getHeader());
        log.info("bearerToken: {}", bearerToken);

        if (Objects.isNull(bearerToken) || !bearerToken.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = bearerToken.replace(jwtConfig.getPrefix(), "");

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)))
                    .build().parseSignedClaims(token).getPayload();

            String username = claims.getSubject();
            log.info("username: {}", username);

            if (Objects.nonNull(username)) {
                List<String> authorities = (List<String>) claims.get("authorities");

                log.info("authorities: {}", authorities.toString());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
*/

