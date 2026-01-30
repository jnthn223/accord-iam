package com.jnthn.accord_iam.scope.web

import com.jnthn.accord_iam.scope.domain.OAuthScope
import java.time.Instant
import java.util.UUID

data class OAuthScopeResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val createdAt: Instant
) {
    companion object {
        fun from(scope: OAuthScope): OAuthScopeResponse =
            OAuthScopeResponse(
                id = scope.id!!,
                name = scope.name,
                description = scope.description,
                createdAt = scope.createdAt
            )
    }
}
