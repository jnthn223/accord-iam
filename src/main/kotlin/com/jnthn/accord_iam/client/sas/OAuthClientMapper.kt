package com.jnthn.accord_iam.client.sas

import com.jnthn.accord_iam.client.domain.OAuthClient
import org.springframework.security.oauth2.core.*
import org.springframework.security.oauth2.server.authorization.client.*
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import java.time.Duration

fun OAuthClient.toRegisteredClient(): RegisteredClient {
    return RegisteredClient.withId(this.id.toString())
        .clientId(this.clientId)
        .clientSecret(this.clientSecret)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .redirectUri("http://localhost:3000/callback") // MVP static
        .scopes { scopes ->
            this.scopes.forEach { scope ->
                scopes.add(scope.name)
            }
        }
        .tokenSettings(
            TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(15))
                .refreshTokenTimeToLive(Duration.ofDays(30))
                .reuseRefreshTokens(false)
                .build()
        )
        .clientSettings(
            ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .build()
        )
        .build()
}