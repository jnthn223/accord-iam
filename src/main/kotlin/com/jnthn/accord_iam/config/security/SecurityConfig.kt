package com.jnthn.accord_iam.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    @Order(2)
    fun adminSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/login", "/error").permitAll()
                    .requestMatchers("/api/admin/**").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin {
                it.successHandler { _, response, _ ->
                    response.status = 200
                }
            }
            .logout { }

        return http.build()
    }
}