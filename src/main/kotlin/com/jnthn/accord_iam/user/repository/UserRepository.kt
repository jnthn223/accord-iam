package com.jnthn.accord_iam.user.repository

import com.jnthn.accord_iam.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?

    fun existsByProjectIdAndEmail(
        projectId: UUID,
        email: String
    ): Boolean
}