package com.jnthn.accord_iam.config.authorization

import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.*
import org.springframework.stereotype.Component

@Component
class JwtTokenCustomizer(
    private val clientRepository: OAuthClientRepository
) : OAuth2TokenCustomizer<JwtEncodingContext> {

    override fun customize(context: JwtEncodingContext) {

        // Only customize ACCESS TOKENS
        if (context.tokenType != OAuth2TokenType.ACCESS_TOKEN) {
            return
        }

        val clientId = context.registeredClient.clientId

        val client = clientRepository.findByClientId(clientId)
            ?: return

        context.claims.apply {
            // OAuth-standard-ish
            claim("client_id", client.clientId)

            // Your domain-specific claims
            claim("project_id", client.project.id.toString())

        }
    }
}