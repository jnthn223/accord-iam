package com.jnthn.accord_iam.user.service

import com.jnthn.accord_iam.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ProjectUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException(username)

        return org.springframework.security.core.userdetails.User
            .withUsername(user.email)
            .password(user.password)
            .disabled(!user.enabled)
            .authorities("ROLE_USER")
            .build()
    }
}