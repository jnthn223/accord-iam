package com.jnthn.accord_iam.project.web

import com.jnthn.accord_iam.project.domain.Project
import java.time.Instant
import java.util.UUID

data class ProjectResponse(
    val id: UUID,
    val name: String,
    val createdAt: Instant
) {
    companion object {
        fun from(project: Project): ProjectResponse =
            ProjectResponse(
                id = project.id!!,
                name = project.name,
                createdAt = project.createdAt
            )
    }
}
