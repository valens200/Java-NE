package rca.templates.valens.v1.config;
import rca.templates.valens.v1.dtos.response.CustomAutError;
import rca.templates.valens.v1.security.jwt.JWTAuthFilter;
import rca.templates.valens.v1.security.User.UserSecurityDetailsService;
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
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpConfig {

    private  CustomAutError authError;
    private JWTAuthFilter authFilter;
    private UserSecurityDetailsService userSecurityDetailsService;

    @Autowired
    public HttpConfig(CustomAutError customAutError, JWTAuthFilter authFilter, UserSecurityDetailsService securityDetailsService){
        this.authError = customAutError;
        this.authFilter = authFilter;
        this.userSecurityDetailsService = securityDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(req -> req.antMatchers("/api/v1/auth/**","/api/v1/users/admin/create").permitAll()
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
                                ).permitAll().anyRequest().authenticated()
                ).sessionManagement(sessionManagement ->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex ->ex.authenticationEntryPoint(authError))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  UserDetailsService userDetailsService(){
        return userSecurityDetailsService::loadUserByUsername;
    }
}
