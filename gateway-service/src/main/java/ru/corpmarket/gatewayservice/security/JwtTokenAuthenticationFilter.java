package ru.corpmarket.gatewayservice.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                   @NonNull HttpServletResponse httpServletResponse,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getRequestURI().equals("/auth")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            log.info("auth");
            return;
        }

        String bearerToken = httpServletRequest.getHeader(jwtConfig.getHeader());
        log.info("bearerToken: {}", bearerToken);
        log.info("filter" );

        if (Objects.isNull(bearerToken) || !bearerToken.startsWith(jwtConfig.getPrefix())) {
            log.info(filterChain.toString());
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            log.info("otlov");
            return;
        }

        String token = bearerToken.replace(jwtConfig.getPrefix(), "");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

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
            log.info("error");
            SecurityContextHolder.clearContext();
        }
        log.info("filter");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
