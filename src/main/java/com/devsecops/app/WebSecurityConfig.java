package com.devsecops.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true") // Redirige con un parámetro si falla
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")  // Endpoint de logout
                        .logoutSuccessUrl("/login?logout")  // Redirige al login tras cerrar sesión
                        .invalidateHttpSession(true)  // Invalida la sesión
                        .deleteCookies("JSESSIONID")  // Borra cookies de sesión
                        .permitAll()
                );

        System.out.println("SecurityFilterChain configured with /logout endpoint");
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);
        return new InMemoryUserDetailsManager(
                users.username("user").password("password").roles("USER").build(),
                users.username("admin").password("password").roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}