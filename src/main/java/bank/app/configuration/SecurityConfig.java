package bank.app.configuration;

import bank.app.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import bank.app.security.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(au -> au
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/login_info").permitAll()
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers("/",
                                "/index.html",
                                "/users",
                                "/users/**",
                                "/accounts/**",
                                "/transactions/**").hasRole(Role.ROLE_ADMIN.getShortRole())
                        .requestMatchers(HttpMethod.GET,
                                "/users/{id}",
                                "/users/{id}/private_info").hasRole(Role.ROLE_CUSTOMER.getShortRole())

                        .requestMatchers(HttpMethod.PUT,
                                "/users/{id}",
                                "/users/{id}/private_info",
                                "/users/{id}/private_info/address").hasRole(Role.ROLE_CUSTOMER.getShortRole())
                        .requestMatchers(HttpMethod.POST,
                                "/users/",
                                "/users/{id}/private_info/").hasRole(Role.ROLE_CUSTOMER.getShortRole())

                        .requestMatchers("/index.html",
                                "/users",
                                "/users/**",
                                "/accounts/**",
                                "/transactions/**").hasRole(Role.ROLE_MANAGER.getShortRole())

                        .anyRequest().hasRole(Role.ROLE_ADMIN.getShortRole()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}