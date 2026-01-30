package com.jnthn.accord_iam.client.web

import com.jnthn.accord_iam.account.security.AccountUserDetails
import com.jnthn.accord_iam.client.service.OAuthClientService
import com.jnthn.accord_iam.project.service.ProjectResolver
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
        @RequestBody request: CreateClientRequest
    ): OAuthClientResponse {
        val project = projectResolver.resolve(projectId, user.getAccount())

        val client = clientService.createClient(project, request.name)

        return OAuthClientResponse.from(client)
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
}