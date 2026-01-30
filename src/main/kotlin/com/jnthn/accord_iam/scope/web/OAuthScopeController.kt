package com.jnthn.accord_iam.scope.web

import com.jnthn.accord_iam.account.security.AccountUserDetails
import com.jnthn.accord_iam.project.service.ProjectResolver
import com.jnthn.accord_iam.scope.service.OAuthScopeService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/admin/projects/{projectId}/scopes")
@Validated
class OAuthScopeController(
    private val projectResolver: ProjectResolver,
    private val scopeService: OAuthScopeService
) {

    @PostMapping
    fun createScope(
        @PathVariable projectId: UUID,
        @AuthenticationPrincipal user: AccountUserDetails,
        @RequestBody request: CreateScopeRequest
    ): OAuthScopeResponse {
        val project = projectResolver.resolve(projectId, user.getAccount())

        val scope = scopeService.createScope(
            project = project,
            name = request.name,
            description = request.description
        )

        return OAuthScopeResponse.from(scope)
    }

    @GetMapping
    fun listScopes(
        @PathVariable projectId: UUID,
        @AuthenticationPrincipal user: AccountUserDetails
    ): List<OAuthScopeResponse> {
        val project = projectResolver.resolve(projectId, user.getAccount())

        return scopeService
            .listScopes(project)
            .map(OAuthScopeResponse::from)
    }
}