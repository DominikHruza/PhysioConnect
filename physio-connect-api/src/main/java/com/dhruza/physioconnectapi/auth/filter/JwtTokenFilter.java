package com.dhruza.physioconnectapi.auth.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dhruza.physioconnectapi.auth.model.AppUser;
import com.dhruza.physioconnectapi.auth.model.SecurityUser;
import com.dhruza.physioconnectapi.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtService.decodeJwt(token);

                String username = decodedJWT.getSubject();
                Collection<SimpleGrantedAuthority> authorities =
                        jwtService.parseRolesFromToken(decodedJWT);


                final AppUser appUser = new AppUser();
                appUser.setEmail(decodedJWT.getSubject());
                appUser.setId(decodedJWT.getClaim("sub_id").asLong());
                final SecurityUser securityUser = new SecurityUser(appUser);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch(Exception exception){
                log.error("Error logging in: {}", exception.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/login");
    }
}
