package com.jnthn.accord_iam.config.authorization

import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.stereotype.Component

@Component
class JwtTokenCustomizer(
    private val clientRepository: OAuthClientRepository
) : OAuth2TokenCustomizer<JwtEncodingContext> {

    override fun customize(context: JwtEncodingContext) {

        if (context.tokenType != OAuth2TokenType.ACCESS_TOKEN) return

        val registeredClient = context.registeredClient
        val grantType = context.authorizationGrantType

        // Always include client_id
        context.claims.claim("client_id", registeredClient.clientId)

        val client = clientRepository.findByClientId(registeredClient.clientId)
            ?: throw IllegalStateException("OAuth client not found")

        // Always include project_id
        context.claims.claim("project_id", client.project.id.toString())

        // Explicit scopes
        context.claims.claim(
            "scope",
            context.authorizedScopes.joinToString(" ")
        )

        // Client metadata
        if (client.metadata["playground"] == true) {
            context.claims.claim("playground", true)
        }

        when (grantType) {

            AuthorizationGrantType.CLIENT_CREDENTIALS -> {
                // Client = subject
                context.claims.claim("sub", registeredClient.clientId)
            }

            AuthorizationGrantType.AUTHORIZATION_CODE -> {
                // User = subject
                val username = context.authorization?.principalName
                    ?: throw IllegalStateException("Authorization principal missing")

                context.claims.claim("sub", username)
                context.claims.claim("user_email", username)
            }
        }
    }
}