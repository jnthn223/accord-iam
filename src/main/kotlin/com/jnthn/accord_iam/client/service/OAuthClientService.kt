package com.jnthn.accord_iam.client.service

import com.jnthn.accord_iam.client.domain.OAuthClient
import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import com.jnthn.accord_iam.project.domain.Project
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class OAuthClientService(
    private val repository: OAuthClientRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createClient(
        project: Project,
        name: String
    ): OAuthClient {
        val clientId = generateClientId(name)
        val rawSecret = UUID.randomUUID().toString()

        val client = OAuthClient(
            clientId = clientId,
            clientSecret = passwordEncoder.encode(rawSecret),
            project = project
        )

        repository.save(client)

        // IMPORTANT: return raw secret only once
        return client.copy(clientSecret = rawSecret)
    }

    fun getClients(project: Project): List<OAuthClient> =
        repository.findByProjectId(project.id!!)

    private fun generateClientId(name: String): String =
        name.lowercase()
            .replace("\\s+".toRegex(), "-")
            .plus("-")
            .plus(UUID.randomUUID().toString().take(8))
}