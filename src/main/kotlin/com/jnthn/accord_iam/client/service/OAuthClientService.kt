package com.jnthn.accord_iam.client.service

import com.jnthn.accord_iam.client.domain.OAuthClient
import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import com.jnthn.accord_iam.client.web.CreateClientRequest
import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.scope.repository.OAuthScopeRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.Collections.emptySet
import java.util.UUID


@Service
class OAuthClientService(
    private val clientRepository: OAuthClientRepository,
    private val scopeRepository: OAuthScopeRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createClient(
        project: Project,
        request: CreateClientRequest
    ): OAuthClient {

        val scopes = if (request.scopes.isEmpty()) {
            emptySet()
        } else {
            val resolved = scopeRepository
                .findByProjectAndNameIn(project, request.scopes)
                .toSet()

            if (resolved.size != request.scopes.size) {
                throw IllegalArgumentException("One or more scopes do not exist in this project")
            }

            resolved
        }

        val rawSecret = UUID.randomUUID().toString()

        val client = OAuthClient(
            project = project,
            clientId = generateClientId(request.name),
            clientSecret = passwordEncoder.encode(rawSecret),
//            redirectUris = request.redirectUris,
            scopes = scopes,
            createdAt = Instant.now()
        )

        return clientRepository.save(client)
    }

    fun getClients(project: Project): List<OAuthClient> =
        clientRepository.findByProjectId(project.id!!)

    fun getClient(
        clientId: String,
        project: Project
    ): OAuthClient {
        return clientRepository
            .findByClientIdAndProjectId(clientId, project.id)
            ?: throw IllegalArgumentException("Client not found in project")
    }

    @Transactional
    fun updateClientScopes(
        project: Project,
        client: OAuthClient,
        scopeNames: Set<String>
    ): OAuthClient {

        if (client.project.id != project.id) {
            throw IllegalStateException("Client does not belong to project")
        }

        val scopes = scopeRepository
            .findByProjectAndNameIn(project, scopeNames)
            .toSet()

        if (scopes.size != scopeNames.size) {
            throw IllegalArgumentException("One or more scopes do not exist in this project")
        }

        return clientRepository.save(
            client.copy(scopes = scopes)
        )
    }

    private fun generateClientId(name: String): String =
        name.lowercase()
            .replace("\\s+".toRegex(), "-")
            .plus("-")
            .plus(UUID.randomUUID().toString().take(8))
}