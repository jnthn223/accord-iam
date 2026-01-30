package com.jnthn.accord_iam.project.service

import com.jnthn.accord_iam.account.domain.Account
import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.project.repository.ProjectRepository
import com.jnthn.accord_iam.shared.exception.ForbiddenException
import com.jnthn.accord_iam.shared.exception.NotFoundException

import org.springframework.stereotype.Component
import java.util.UUID


@Component
class ProjectResolver(
    private val projectRepository: ProjectRepository
) {

    fun resolve(projectId: UUID, account: Account): Project {
        val project = projectRepository.findById(projectId)
            .orElseThrow {
                NotFoundException("Project not found")
            }

        if (project.account.id != account.id) {
            throw ForbiddenException("Project does not belong to this account")
        }

        return project
    }
}