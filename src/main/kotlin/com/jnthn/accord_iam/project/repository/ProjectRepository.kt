package com.jnthn.accord_iam.project.repository

import com.jnthn.accord_iam.project.domain.Project
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectRepository : JpaRepository<Project, UUID> {

    fun findByAccountId(accountId: UUID): List<Project>
}