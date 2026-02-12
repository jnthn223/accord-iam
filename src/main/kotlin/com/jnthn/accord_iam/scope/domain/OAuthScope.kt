package com.jnthn.accord_iam.scope.domain

import com.jnthn.accord_iam.project.domain.Project
import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "oauth_scopes",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["project_id", "name"])
    ]
)
data class OAuthScope(

    // TODO: Revamp scopes database

    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: Project,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)
