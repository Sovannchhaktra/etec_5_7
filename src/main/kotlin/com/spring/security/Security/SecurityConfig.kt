package com.spring.security.Security

import com.spring.security.utils.Constant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user1: UserDetails = User.withUsername("user1")
            .password(passwordEncoder().encode("123"))
            .roles("USER")
            .build()

        val user2: UserDetails = User.withUsername("admin")
            .password(passwordEncoder().encode("12345"))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(user1, user2)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { req ->
                req
                    .requestMatchers(Constant.BASE_URL+ "/home"+ Constant.BASE_LIST)
                    .hasAnyRole("USER", "ADMIN")
                    .anyRequest()
                    .authenticated()
            }
            .httpBasic { httpBasic ->
                httpBasic.authenticationEntryPoint { _, res, _ ->
                    res.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
                }
            }
            .csrf { it.disable() }
            .sessionManagement { sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }

        return http.build();
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}