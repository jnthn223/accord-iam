package com.jnthn.accord_iam.account.service

import com.jnthn.accord_iam.account.domain.Account
import com.jnthn.accord_iam.account.repository.AccountRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AccountBootstrap {

    @Bean
    fun bootstrapAccount(
        accountRepository: AccountRepository,
        passwordEncoder: PasswordEncoder
    ) = CommandLineRunner {

        if (!accountRepository.existsByEmail("admin@accord1.dev")) {
            val account = Account(
                email = "admin@accord1.dev",
                password = passwordEncoder.encode("password")
            )

            accountRepository.save(account)

            println("✅ Dev account created: admin@accord1.dev / password")
        }
    }
}