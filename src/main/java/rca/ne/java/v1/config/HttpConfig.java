package rca.ne.java.v1.config;

import rca.ne.java.v1.dtos.response.CustomAutError;
import rca.ne.java.v1.security.jwt.JWTAuthFilter;
import rca.ne.java.v1.security.User.UserSecurityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Lombok annotation for constructor injection
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enables Spring Security global method security
public class HttpConfig {

    // Fields for dependency injection
    private CustomAutError authError;
    private JWTAuthFilter authFilter;
    private UserSecurityDetailsService userSecurityDetailsService;

    // Constructor for dependency injection using @Autowired (Lombok RequiredArgsConstructor handles this)
    @Autowired
    public HttpConfig(CustomAutError customAutError, JWTAuthFilter authFilter, UserSecurityDetailsService securityDetailsService){
        this.authError = customAutError;
        this.authFilter = authFilter;
        this.userSecurityDetailsService = securityDetailsService;
    }

    // Bean definition for security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(req -> req
                        // Permit access to certain endpoints without authentication
                        .antMatchers("/api/v1/auth/**","/api/v1/users/admin/create").permitAll()
                        // Permit access to Swagger and other API documentation endpoints
                        .antMatchers(
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/api/v1/auth/verify-account",
                                "/api/v1/auth/reset-password",
                                "/api/v1/files/**"
                        ).permitAll()
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authError)) // Handle authentication errors
                .authenticationProvider(authenticationProvider()) // Configure authentication provider
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter
        return http.build();
    }

    // Bean definition for DaoAuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Set custom UserDetailsService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return daoAuthenticationProvider;
    }

    // Bean definition for BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Bean definition for UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(){
        return userSecurityDetailsService::loadUserByUsername; // Return UserDetailsService implementation
    }
}
