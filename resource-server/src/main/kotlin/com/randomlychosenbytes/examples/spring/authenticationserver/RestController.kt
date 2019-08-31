package com.randomlychosenbytes.examples.spring.authenticationserver

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@PreAuthorize("hasAuthority('ROLE_USER')")
@RestController
class RestController {
    @GetMapping("/hello")
    fun test() = "Hello"
}