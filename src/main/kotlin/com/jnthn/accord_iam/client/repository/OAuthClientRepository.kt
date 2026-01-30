package com.jnthn.accord_iam.client.repository

import com.jnthn.accord_iam.client.domain.OAuthClient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface OAuthClientRepository : JpaRepository<OAuthClient, UUID> {

    fun findByProjectId(projectId: UUID): List<OAuthClient>

    fun findByClientIdAndProjectId(
        clientId: String,
        projectId: UUID?
    ): OAuthClient?

    fun findByClientId(
        clientId: String
    ): OAuthClient?

    @Query(
        """
    select c from OAuthClient c
    left join fetch c.scopes
    where c.clientId = :clientId
    """
    )
    fun findByClientIdWithScopes(
        @Param("clientId") clientId: String
    ): OAuthClient?
}