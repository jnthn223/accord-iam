package com.jnthn.accord_iam.client.web

import com.jnthn.accord_iam.client.domain.OAuthClient
import java.time.Instant

data class OAuthClientResponse(
    val clientId: String,
    val clientSecret: String?,
    val redirectUris: Set<String> = emptySet(),
    val scopes: Set<String> = emptySet(),
    val createdAt: Instant
) {
    companion object {
        fun from(client: OAuthClient, includeSecret: Boolean = false): OAuthClientResponse =
            OAuthClientResponse(
                clientId = client.clientId,
                clientSecret = if (includeSecret) client.clientSecret else null,
//                redirectUris = client.redirectUris,
                scopes = client.scopes.map { it.name }.toSet(),
                createdAt = client.createdAt
            )
    }
}
