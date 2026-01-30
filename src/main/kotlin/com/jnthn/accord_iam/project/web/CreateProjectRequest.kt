package com.jnthn.accord_iam.project.web

import jakarta.validation.constraints.NotBlank

data class CreateProjectRequest(
    @field:NotBlank
    val name: String
)
