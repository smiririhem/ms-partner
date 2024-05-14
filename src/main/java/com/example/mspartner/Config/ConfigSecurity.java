
package com.example.mspartner.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.http.SessionCreationPolicy;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecurity {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http)
           throws Exception {
       http.csrf(t -> t.disable());
       http.authorizeHttpRequests(authorize -> {
           authorize
                   .requestMatchers(HttpMethod.POST, "/partners/create").permitAll()
                   .requestMatchers(HttpMethod.POST, "/partners/upload").permitAll()
                   .requestMatchers(HttpMethod.GET, "/partners/display").permitAll()
                   .requestMatchers(HttpMethod.POST, "/partner_solution/upload").permitAll()
                   .requestMatchers(HttpMethod.GET, "/partner_solution/all").permitAll()
                   .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                   .anyRequest().authenticated();
       });
       http.oauth2ResourceServer(t-> {
           t.jwt(Customizer.withDefaults());
           //t.opaqueToken(Customizer.withDefaults());
       });
       http.sessionManagement(
               t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       );
       return http.build();
   }
   }


