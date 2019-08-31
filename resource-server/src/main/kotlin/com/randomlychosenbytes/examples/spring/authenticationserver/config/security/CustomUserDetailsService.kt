package com.randomlychosenbytes.examples.spring.authenticationserver.config.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    override fun loadUserByUsername(ssoId: String): UserDetails {

        return object : UserDetails {

            override fun getAuthorities() = listOf("ROLE_USER").map(::SimpleGrantedAuthority).toMutableList()

            override fun isEnabled() = true

            override fun getUsername() = ssoId

            override fun isCredentialsNonExpired(): Boolean {
                return true
            }

            override fun getPassword(): String {
                return "\$2a\$10\$TaFhCH12gERWuZXXM1PCwepWd5kOKjk/zM/C1VABQVTJyDG0lKYti"
            }

            override fun isAccountNonExpired(): Boolean {
                return true
            }

            override fun isAccountNonLocked(): Boolean {
                return true
            }
        }
    }
}