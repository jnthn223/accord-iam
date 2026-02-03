package com.jnthn.accord_iam.playground.admin.dto

data class PlaygroundClientResponse(
    val clientId: String,
    val clientSecret: String,
    val scopes: List<String>
)
