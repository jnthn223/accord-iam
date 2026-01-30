package com.jnthn.accord_iam.client.web

import com.jnthn.accord_iam.account.security.AccountUserDetails
import com.jnthn.accord_iam.client.service.OAuthClientService
import com.jnthn.accord_iam.project.service.ProjectResolver
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/admin/projects/{projectId}/clients")
@Validated
class OAuthClientController(
    private val projectResolver: ProjectResolver,
    private val clientService: OAuthClientService
) {

    @PostMapping
    fun createClient(
        @PathVariable projectId: UUID,
        @AuthenticationPrincipal user: AccountUserDetails,
        @RequestBody @Valid request: CreateClientRequest
    ): OAuthClientResponse {

        val project = projectResolver.resolve(projectId, user.getAccount())
        val client = clientService.createClient(project, request)

        // include secret ONLY on creation
        return OAuthClientResponse.from(client, includeSecret = true)
    }

    @GetMapping
    fun listClients(
        @PathVariable projectId: UUID,
        @AuthenticationPrincipal user: AccountUserDetails
    ): List<OAuthClientResponse> {
        val project = projectResolver.resolve(projectId, user.getAccount())

        return clientService
            .getClients(project)
            .map(OAuthClientResponse::from)
    }

    @PutMapping("/{clientId}/scopes")
    fun updateClientScopes(
        @PathVariable projectId: UUID,
        @PathVariable clientId: String,
        @AuthenticationPrincipal user: AccountUserDetails,
        @RequestBody request: UpdateClientScopesRequest
    ): OAuthClientResponse {

        val project = projectResolver.resolve(projectId, user.getAccount())
        val client = clientService.getClient(clientId, project)

        val updated = clientService.updateClientScopes(
            project = project,
            client = client,
            scopeNames = request.scopes
        )

        return OAuthClientResponse.from(updated)
    }
}