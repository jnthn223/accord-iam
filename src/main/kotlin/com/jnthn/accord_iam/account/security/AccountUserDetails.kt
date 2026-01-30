package com.jnthn.accord_iam.account.security

import com.jnthn.accord_iam.account.domain.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AccountUserDetails(
    private val account: Account
) : UserDetails {

    override fun getUsername(): String = account.email

    override fun getPassword(): String? = account.password

    override fun getAuthorities(): Collection<GrantedAuthority> =
        emptyList() // no roles yet

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getAccount(): Account = account
}