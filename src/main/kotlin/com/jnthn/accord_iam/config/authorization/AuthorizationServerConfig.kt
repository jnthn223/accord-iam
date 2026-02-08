package com.jnthn.accord_iam.config.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Configuration
class AuthorizationServerConfig {
    // TODO: Configure AUthorization Code Flow
    @Bean
    @Order(1)
    fun authorizationServerSecurityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {

        val authorizationServerConfigurer =
            OAuth2AuthorizationServerConfigurer()

        authorizationServerConfigurer
            .oidc { }

        http
            .securityMatcher(authorizationServerConfigurer.endpointsMatcher)
            .with(authorizationServerConfigurer) { }
            .authorizeHttpRequests {
                it.requestMatchers("/.well-known/**").permitAll()
                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(
                    LoginUrlAuthenticationEntryPoint("/login")
                )
            }

        return http.build()
    }
}