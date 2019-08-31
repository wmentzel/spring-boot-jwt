package com.randomlychosenbytes.examples.spring.authorizationserver.config.security.optional

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import java.util.*

// optional
class CustomTokenEnhancer : TokenEnhancer {
    override fun enhance(
            accessToken: OAuth2AccessToken,
            authentication: OAuth2Authentication
    ): OAuth2AccessToken {

        val additionalInfo = HashMap<String, Any>()
        additionalInfo["organization"] = "Example Corp."
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo

        return accessToken
    }
}