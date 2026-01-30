package com.jnthn.accord_iam.client.repository

import com.jnthn.accord_iam.client.domain.OAuthClient
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OAuthClientRepository : JpaRepository<OAuthClient, UUID> {

    fun findByProjectId(projectId: UUID): List<OAuthClient>

    fun findByClientIdAndProjectId(
        clientId: String,
        projectId: UUID
    ): OAuthClient?
}