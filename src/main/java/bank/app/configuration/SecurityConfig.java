package bank.app.configuration;

import bank.app.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .cors(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(au -> au
                            .requestMatchers("/", "/index.html").hasRole(Role.ROLE_ADMIN.getShortRole()) // добавляем доступ к index.html
                            .requestMatchers("/swagger-ui/**").hasRole(Role.ROLE_ADMIN.getShortRole())
                            .requestMatchers("accounts").hasRole(Role.ROLE_ADMIN.getShortRole())
                            .requestMatchers(("transaction")).hasRole(Role.ROLE_ADMIN.getShortRole())
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(Customizer.withDefaults())
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID"))
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
//        return new BCryptPasswordEncoder();
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


}