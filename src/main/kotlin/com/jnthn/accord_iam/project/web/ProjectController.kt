package com.jnthn.accord_iam.project.web

import com.jnthn.accord_iam.account.security.AccountUserDetails
import com.jnthn.accord_iam.project.service.ProjectResolver
import com.jnthn.accord_iam.project.service.ProjectService
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
@RequestMapping("/api/admin/projects")
@Validated
class ProjectController(
    private val projectService: ProjectService,
    private val projectResolver: ProjectResolver
) {

    @PostMapping
    fun createProject(
        @AuthenticationPrincipal user: AccountUserDetails,
        @RequestBody request: CreateProjectRequest
    ): ProjectResponse {
        val project = projectService.createProject(
            account = user.getAccount(),
            name = request.name
        )

        return ProjectResponse.from(project)
    }

    @GetMapping
    fun listProjects(
        @AuthenticationPrincipal user: AccountUserDetails
    ): List<ProjectResponse> =
        projectService
            .getProjectsForAccount(user.getAccount())
            .map(ProjectResponse::from)

    @GetMapping("/{projectId}")
    fun getProject(
        @PathVariable projectId: UUID,
        @AuthenticationPrincipal user: AccountUserDetails
    ): ProjectResponse {
        val project = projectResolver.resolve(
            projectId = projectId,
            account = user.getAccount()
        )

        return ProjectResponse.from(project)
    }
}