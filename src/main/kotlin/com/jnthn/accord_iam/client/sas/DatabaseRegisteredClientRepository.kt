package com.jnthn.accord_iam.client.sas

import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import org.springframework.security.oauth2.server.authorization.client.*
import org.springframework.stereotype.Component
import java.util.*


@Component
class DatabaseRegisteredClientRepository(
    private val clientRepository: OAuthClientRepository,
) : RegisteredClientRepository {

    override fun save(registeredClient: RegisteredClient) {
        // We DO NOT allow SAS to persist clients
        // All client persistence goes through admin APIs
        throw UnsupportedOperationException("Client persistence is managed by Accord IAM")
    }

    override fun findById(id: String): RegisteredClient? {
        return clientRepository.findById(UUID.fromString(id))
            .orElse(null)
            ?.toRegisteredClient()
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        val client = clientRepository.findByClientIdWithScopes(clientId)
            ?: return null

        return client.toRegisteredClient()
    }
}