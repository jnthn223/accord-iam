package com.jnthn.accord_iam.account.web

import com.jnthn.accord_iam.account.security.AccountUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AccountController {

    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal user: AccountUserDetails
    ): Map<String, Any?> {
        val account = user.getAccount()

        return mapOf(
            "id" to account.id,
            "email" to account.email,
            "createdAt" to account.createdAt
        )
    }
}