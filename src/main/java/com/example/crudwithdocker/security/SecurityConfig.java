package com.example.crudwithdocker.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf( csrs -> csrs.disable())
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( authz -> {


                       authz.requestMatchers(HttpMethod.POST , "/user").hasAnyRole("ADMIN");
                       authz.requestMatchers(HttpMethod.PUT , "/user").hasAnyRole("ADMIN");
                       authz.requestMatchers(HttpMethod.DELETE , "/user").hasAnyRole("ADMIN");
                       authz.requestMatchers(HttpMethod.GET , "/user").hasAnyRole("ADMIN");


                       authz.requestMatchers(HttpMethod.POST , "auth/login").permitAll();
                       authz.requestMatchers(HttpMethod.POST , "auth/create").permitAll()

                       .anyRequest().authenticated();


                }).build();
       }
       @Bean
       public AuthenticationManager authenticationManager
               (AuthenticationConfiguration configuration) throws Exception {
               return configuration.getAuthenticationManager();
       }

       @Bean
       public PasswordEncoder passwordEncoder(){
           return new BCryptPasswordEncoder();
       }

}