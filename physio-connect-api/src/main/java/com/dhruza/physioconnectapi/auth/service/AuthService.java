package com.dhruza.physioconnectapi.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dhruza.physioconnectapi.auth.model.AppUser;
import com.dhruza.physioconnectapi.auth.model.SecurityUser;
import com.dhruza.physioconnectapi.auth.repository.AppUserRepository;
import com.dhruza.physioconnectapi.dto.JwtTokenResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        AppUser u = user.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials!"));
        return new SecurityUser(u);
    }

    public JwtTokenResponse generateToken(Authentication authentication, String issuer){
        if(!authentication.isAuthenticated()){
            throw new SecurityException("Invalid credentials");
        }

        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        String accessToken = jwtService.createAccessToken(user, issuer);
        String refreshToken = jwtService.createRefreshToken(user, issuer);

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtTokenResponse getAccessTokenFromRefreshToken(String authorizationHeader, String issuer){

        String refreshToken = authorizationHeader.substring("Bearer ".length());
        DecodedJWT decodedJWT = jwtService.decodeJwt(refreshToken);

        String email = decodedJWT.getSubject();
        SecurityUser user = (SecurityUser) loadUserByUsername(email);

        final String accessToken = jwtService.createAccessToken(user, issuer);
        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
