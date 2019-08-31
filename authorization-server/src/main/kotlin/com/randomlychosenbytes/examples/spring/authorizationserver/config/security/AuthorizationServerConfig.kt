package com.randomlychosenbytes.examples.spring.authorizationserver.config.security

import com.randomlychosenbytes.examples.spring.authorizationserver.config.security.optional.CustomTokenEnhancer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
        private val authenticationManager: AuthenticationManager,
        private val userDetailsService: CustomUserDetailsService
) : AuthorizationServerConfigurerAdapter() {

    companion object {
        const val expirationTimeMin = 15
    }

    override fun configure(configurer: ClientDetailsServiceConfigurer) {
        configurer
                .inMemory()
                .withClient("testClient")
                .secret("{noop}testClientSecret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .accessTokenValiditySeconds(expirationTimeMin * 60)
                .refreshTokenValiditySeconds(expirationTimeMin * 60)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {

        // the usage of the CustomTokenEnhancer is optional
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(
                listOf(CustomTokenEnhancer(), accessTokenConverter())
        )

        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
    }

    @Bean
    fun tokenStore() = JwtTokenStore(accessTokenConverter())

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        val keyStoreKeyFactory = KeyStoreKeyFactory(ClassPathResource("test.jks"), "testpass".toCharArray())
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("testkey"))
        return converter
    }

    @Bean
    @Primary // Making this primary to avoid any accidental duplication with another token service instance of the same name
    fun tokenServices() = DefaultTokenServices().apply {
        setTokenStore(tokenStore())
        setSupportRefreshToken(true)
    }
}