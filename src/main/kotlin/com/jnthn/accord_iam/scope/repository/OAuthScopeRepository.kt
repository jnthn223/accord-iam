package com.jnthn.accord_iam.scope.repository

import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.scope.domain.OAuthScope
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OAuthScopeRepository : JpaRepository<OAuthScope, UUID> {

    fun findByProjectId(projectId: UUID): List<OAuthScope>

    fun findByNameAndProjectId(
        name: String,
        projectId: UUID
    ): OAuthScope?

    fun findByProjectAndNameIn(
        project: Project,
        names: Set<String>
    ): List<OAuthScope>
}