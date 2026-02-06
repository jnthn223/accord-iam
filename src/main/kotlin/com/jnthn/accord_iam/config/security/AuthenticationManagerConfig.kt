package com.jnthn.accord_iam.config.security

import com.jnthn.accord_iam.account.security.AccountUserDetailsService
import com.jnthn.accord_iam.user.service.ProjectUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AuthenticationManagerConfig(
    private val developerUserDetailsService: AccountUserDetailsService,
    private val projectUserDetailsService: ProjectUserDetailsService,
    private var passwordEncoder: PasswordEncoder
) {

    @Bean
    fun authenticationManager(): AuthenticationManager {

        val developerProvider = DaoAuthenticationProvider(developerUserDetailsService).apply {
            passwordEncoder = passwordEncoder
        }

        val projectProvider = DaoAuthenticationProvider(projectUserDetailsService).apply {
            passwordEncoder = passwordEncoder
        }

        return ProviderManager(
            listOf(
                developerProvider,
                projectProvider
            )
        )
    }
}