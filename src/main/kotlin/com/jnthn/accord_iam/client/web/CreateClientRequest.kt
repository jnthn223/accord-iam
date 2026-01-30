package com.jnthn.accord_iam.client.web

import jakarta.validation.constraints.NotBlank

data class CreateClientRequest(
    @field:NotBlank
    val name: String
)
