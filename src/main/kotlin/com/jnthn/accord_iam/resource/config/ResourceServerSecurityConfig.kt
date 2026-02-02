package com.jnthn.accord_iam.resource.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ResourceServerSecurityConfig {

    @Bean
    @Order(3)
    fun resourceServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            // Only applies to resource APIs
            .securityMatcher("/api/resource/**")

            // Stateless API
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            // CSRF not needed for token-based APIs
            .csrf { it.disable() }

            // Authorization rules
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/resource/**").authenticated()
                    .anyRequest().denyAll()
            }

            // JWT validation
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }

        return http.build()
    }

    /**
     * Converts `scope` claim → Spring authorities:
     *   scope=write:profile → SCOPE_write:profile
     */
    private fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val scopesConverter = JwtGrantedAuthoritiesConverter().apply {
            setAuthorityPrefix("SCOPE_")
            setAuthoritiesClaimName("scope")
        }

        return JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(scopesConverter)
        }
    }
}