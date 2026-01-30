package com.jnthn.accord_iam.scope.web

import jakarta.validation.constraints.NotBlank

data class CreateScopeRequest(
    @field:NotBlank
    val name: String,
    val description: String?
)
