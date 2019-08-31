package com.randomlychosenbytes.examples.spring.authenticationserver.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.io.BufferedReader


@Configuration
@EnableResourceServer
class ResourceServerConfig: ResourceServerConfigurerAdapter() {

    override fun configure(config: ResourceServerSecurityConfigurer) {
        config.tokenServices(DefaultTokenServices().apply {
            setTokenStore(tokenStore())
        })
    }

    @Bean
    fun tokenStore() = JwtTokenStore(accessTokenConverter())

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        val publicKey = ClassPathResource("pubkey.txt")
                .inputStream
                .bufferedReader()
                .use(BufferedReader::readText)

        converter.setVerifierKey(publicKey)
        return converter
    }

    @Bean
    @Primary //Making this primary to avoid any accidental duplication with another token service instance of the same name
    fun tokenServices() = DefaultTokenServices().apply {
        setTokenStore(tokenStore())
        setSupportRefreshToken(true)
    }
}