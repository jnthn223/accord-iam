package com.jnthn.accord_iam.client.domain

import com.jnthn.accord_iam.config.persistence.converter.JsonMapConverter
import com.jnthn.accord_iam.project.domain.Project
import com.jnthn.accord_iam.scope.domain.OAuthScope
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "oauth_clients",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["project_id", "client_id"])
    ]
)
data class OAuthClient(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(name = "client_id", nullable = false)
    val clientId: String,

    @Column(name = "client_secret", nullable = false)
    val clientSecret: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: Project,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "oauth_client_scopes",
        joinColumns = [JoinColumn(name = "client_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    val scopes: Set<OAuthScope> = emptySet(),

    @Convert(converter = JsonMapConverter::class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    val metadata: Map<String, Any> = emptyMap(),

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)