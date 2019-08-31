package com.randomlychosenbytes.examples.spring.authenticationserver.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
        val userDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {

    // seems useless at first but we need to tell spring that this is the manager we chose, by annotation it with @Bean
    @Bean
    override fun authenticationManager(): AuthenticationManager = super.authenticationManager()

    override fun configure(auth: AuthenticationManagerBuilder?) {

        // or: in memory authentication

        auth!!.userDetailsService(userDetailsService)
                .passwordEncoder(BCryptPasswordEncoder(10))
    }

    override fun configure(http: HttpSecurity) {

        http.cors().and()
        http.csrf().disable()
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS)
    }
}