package com.jnthn.accord_iam.account.service

import com.jnthn.accord_iam.account.domain.Account
import com.jnthn.accord_iam.account.repository.AccountRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@Configuration
class AccountBootstrap {

    @Bean
    fun bootstrapAccount(
        accountRepository: AccountRepository,
        passwordEncoder: PasswordEncoder
    ) = CommandLineRunner {

        if (!accountRepository.existsByEmail("admin@accord.dev")) {
            val account = Account(
                email = "admin@accord.dev",
                password = passwordEncoder.encode("password")
            )

            accountRepository.save(account)

            println("✅ Dev account created: admin@accord.dev / password")
        }
    }
}