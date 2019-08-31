package com.randomlychosenbytes.examples.spring.authorizationserver.config.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(ssoId: String): UserDetails {

        return object : UserDetails {

            override fun getAuthorities() = listOf("ROLE_USER").map(::SimpleGrantedAuthority).toMutableList()

            override fun isEnabled() = true

            override fun getUsername() = ssoId

            override fun isCredentialsNonExpired(): Boolean {
                return true // modify as needed
            }

            override fun getPassword(): String {
                // would usually be retrieved from an external data source (e.g. database)
                return "\$2a\$10\$v8fHvzZJBcdGUcCDqITCIekc6C5up4gX9lrTnYHPG6DQu1tfIij6q" // "testUserPassword"
            }

            override fun isAccountNonExpired(): Boolean {
                return true // modify as needed
            }

            override fun isAccountNonLocked(): Boolean {
                return true // modify as needed
            }
        }
    }
}