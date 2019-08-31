package com.randomlychosenbytes.examples.spring.authorizationserver.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsFilterRegistrationBean(): FilterRegistrationBean<*> {

        val config = CorsConfiguration().apply {
            applyPermitDefaultValues()
            allowCredentials = true
            allowedOrigins = listOf("*")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            exposedHeaders = listOf("content-length")
            maxAge = 3600L
        }

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return FilterRegistrationBean(CorsFilter(source)).apply<FilterRegistrationBean<CorsFilter>> {
            setOrder(0)
        }
    }
}