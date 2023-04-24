package com.dhruza.physioconnectapi.auth.config;

import com.dhruza.physioconnectapi.auth.filter.JwtTokenFilter;
import com.dhruza.physioconnectapi.auth.model.RoleType;
import com.dhruza.physioconnectapi.auth.service.AuthService;
import com.dhruza.physioconnectapi.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;
    private final JwtService jwtService;
    private final ApplicationContext applicationContext;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests(requests -> requests
               .requestMatchers("/api/visit/all/practitioner/**").hasRole(
                       RoleType.PRACTITIONER.getName())
               .requestMatchers(HttpMethod.POST, "/api/visit/**").hasRole(
                       RoleType.PRACTITIONER.getName())
               .requestMatchers("/api/visit/all/patient/{patientId}/**").access(getWebExpressionAuthorizationManager(
                       "@checkOwnerService.hasResourceOwnership(authentication,#patientId) and hasRole('PATIENT')"))

               .requestMatchers("/api/patient/register").permitAll()
               .requestMatchers("/api/patient/**").hasRole(
                     RoleType.PRACTITIONER.getName())



               .requestMatchers(HttpMethod.GET, "/api/workout-plan/active/{patientId}").access(
                       getWebExpressionAuthorizationManager(
                               "hasRole('PRACTITIONER') or " +
                               "(@checkOwnerService.hasResourceOwnership(authentication,#patientId) and hasRole('PATIENT'))")
               )
               .requestMatchers(HttpMethod.GET, "/api/workout-plan/all/{patientId}").access(
                       getWebExpressionAuthorizationManager(
                               "hasRole('PRACTITIONER') or " +
                                       "(@checkOwnerService.hasResourceOwnership(authentication,#patientId) and hasRole('PATIENT'))")
               )
               .requestMatchers(HttpMethod.GET, "/api/workout-plan/exercises/**").access(
                       getWebExpressionAuthorizationManager(
                               "hasRole('PRACTITIONER') or hasRole('PATIENT')")
               )
               .requestMatchers(HttpMethod.POST, "/api/workout-plan").hasRole(
                       RoleType.PRACTITIONER.getName())

               .requestMatchers(HttpMethod.PUT, "/api/workout-plan/**").hasRole(
                       RoleType.PRACTITIONER.getName())

               .requestMatchers(HttpMethod.GET, "/api/video-instructions/{title}")
               .permitAll()
               //.hasAnyRole(RoleType.PATIENT.getName(), RoleType.PRACTITIONER.getName())
               .requestMatchers(HttpMethod.POST, "/api/video-instructions/**").hasRole(
                       RoleType.PRACTITIONER.getName())

               .requestMatchers("/api/document/**").hasAnyRole(
                       RoleType.PRACTITIONER.getName())

               .requestMatchers(HttpMethod.GET, "/api/exercise-session/all/**").hasAnyRole(
                       RoleType.PRACTITIONER.getName())
               .requestMatchers(HttpMethod.POST, "/api/exercise-session").hasAnyRole(
                       RoleType.PRACTITIONER.getName(), RoleType.PATIENT.getName())

               .requestMatchers("/api/auth/token/validate").authenticated()
               .requestMatchers("/api/auth/login").permitAll()
               .requestMatchers("/api/auth/token/refresh").permitAll()

               .anyRequest().denyAll()
               .and()
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(new JwtTokenFilter(jwtService), UsernamePasswordAuthenticationFilter.class));
               return http.csrf(csrf -> csrf.disable())
                       .cors().configurationSource(corsConfigurationSource()).and()
                       .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                       .build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(authService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private WebExpressionAuthorizationManager getWebExpressionAuthorizationManager(final String expression) {
        final var expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        final var authorizationManager = new WebExpressionAuthorizationManager(expression);
        authorizationManager.setExpressionHandler(expressionHandler);
        return authorizationManager;
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowOrigins = Arrays.asList("*");
        configuration.setAllowedOriginPatterns(allowOrigins);
        configuration.setAllowedMethods(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        //in case authentication is enabled this flag MUST be set, otherwise CORS requests will fail
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
