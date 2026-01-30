package com.jnthn.accord_iam.project.service

import com.jnthn.accord_iam.account.domain.Account
import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.project.repository.ProjectRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {

    @Transactional
    fun createProject(
        account: Account,
        name: String
    ): Project {
        val project = Project(
            name = name,
            account = account
        )
        return projectRepository.save(project)
    }

    @Transactional(readOnly = true)
    fun getProjectsForAccount(account: Account): List<Project> =
        projectRepository.findByAccountId(account.id!!)
}