package com.jnthn.accord_iam.account.repository

import com.jnthn.accord_iam.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository: JpaRepository<Account, UUID> {
    fun findByEmail(email: String): Account?

    fun existsByEmail(email: String): Boolean
}