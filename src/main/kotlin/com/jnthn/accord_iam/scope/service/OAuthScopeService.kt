package com.jnthn.accord_iam.scope.service

import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.scope.domain.OAuthScope
import com.jnthn.accord_iam.scope.repository.OAuthScopeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OAuthScopeService(
    private val repository: OAuthScopeRepository
) {

    @Transactional
    fun createScope(
        project: Project,
        name: String,
        description: String?
    ): OAuthScope {
        val scope = OAuthScope(
            name = name,
            description = description,
            project = project
        )
        return repository.save(scope)
    }

    @Transactional(readOnly = true)
    fun listScopes(project: Project): List<OAuthScope> =
        repository.findByProjectId(project.id!!)
}