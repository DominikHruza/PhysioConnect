package com.dhruza.physioconnectapi.controller;

import com.dhruza.physioconnectapi.auth.service.AuthService;
import com.dhruza.physioconnectapi.dto.JwtTokenResponse;
import com.dhruza.physioconnectapi.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    ResponseEntity<JwtTokenResponse> login(HttpServletRequest request, @RequestBody LoginDto loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword());

        final Authentication authentication = authenticationManager
                .authenticate(authenticationToken);

        String issuer = ServletUriComponentsBuilder
                .fromCurrentContextPath().build().toUriString();

        final JwtTokenResponse jwtTokenResponse = authService
                .generateToken(authentication, issuer);

        return ResponseEntity.ok(jwtTokenResponse);
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<JwtTokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            throw new SecurityException("Invalid Authorization Header");
        }

        final String issuer = ServletUriComponentsBuilder
                .fromCurrentContextPath().build().toUriString();

        final JwtTokenResponse tokenResponse = authService
                .getAccessTokenFromRefreshToken(authorizationHeader, issuer);

        return ResponseEntity.ok(tokenResponse);
    }

}
