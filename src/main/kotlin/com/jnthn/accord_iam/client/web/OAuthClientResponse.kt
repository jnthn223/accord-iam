package com.jnthn.accord_iam.client.web

import com.jnthn.accord_iam.client.domain.OAuthClient
import java.time.Instant

data class OAuthClientResponse(
    val clientId: String,
    val clientSecret: String?,
    val createdAt: Instant
) {
    companion object {
        fun from(client: OAuthClient): OAuthClientResponse =
            OAuthClientResponse(
                clientId = client.clientId,
                clientSecret = client.clientSecret, // present only on creation
                createdAt = client.createdAt
            )
    }
}
