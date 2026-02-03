package com.jnthn.accord_iam.playground.admin.service

import com.jnthn.accord_iam.client.domain.OAuthClient
import com.jnthn.accord_iam.client.repository.OAuthClientRepository
import com.jnthn.accord_iam.project.repository.ProjectRepository
import com.jnthn.accord_iam.playground.admin.dto.PlaygroundClientResponse
import com.jnthn.accord_iam.playground.admin.dto.PlaygroundStatusResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class PlaygroundAdminService(
    private val projectRepository: ProjectRepository,
    private val clientRepository: OAuthClientRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createPlaygroundClient(projectId: UUID): PlaygroundClientResponse {

        val project = projectRepository.findById(projectId)
            .orElseThrow { IllegalArgumentException("Project not found") }

        // Enforce 1 playground client per project
        clientRepository.findPlaygroundClient(project.id)?.let {
            throw IllegalStateException("Playground client already exists")
        }

        val rawSecret = UUID.randomUUID().toString()

        val client = OAuthClient(
            project = project,
            clientId = generatePlaygroundClientId(),
            clientSecret = passwordEncoder.encode(rawSecret),
//            scopes = project.,
            metadata = mapOf(
                "playground" to true,
                "managed" to true
            ),
            createdAt = Instant.now()
        )

        clientRepository.save(client)

        return PlaygroundClientResponse(
            clientId = client.clientId,
            clientSecret = rawSecret,
            scopes = client.scopes.map { it.name }
        )
    }

    @Transactional(readOnly = true)
    fun getPlaygroundStatus(projectId: UUID): PlaygroundStatusResponse {
        val exists = clientRepository.findPlaygroundClient(projectId) != null
        return PlaygroundStatusResponse(exists)
    }

    @Transactional
    fun deletePlaygroundClient(projectId: UUID) {
        clientRepository.findPlaygroundClient(projectId)?.let {
            clientRepository.delete(it)
        }
    }

    private fun generatePlaygroundClientId(): String =
        "playground-${UUID.randomUUID().toString().take(8)}"
}