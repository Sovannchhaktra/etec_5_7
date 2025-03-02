package com.spring.security.services

import com.spring.security.Security.JwtService
import com.spring.security.dto.UserLogin
import com.spring.security.dto.UserReq
import com.spring.security.entity.Users
import com.spring.security.repository.UserRepository
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun createUser(req: UserReq): String{
        val pass = passwordEncoder.encode(req.password)
        val user = Users(
            username = req.username,
            password = pass,
            email = req.email,
            gender = req.gender,
        )
        userRepository.save(user)
        return "Created user successfully"
    }

    fun login(req: UserLogin): String {

        val user = userRepository.findByUsername(req.username)
            ?: throw UsernameNotFoundException("User not found")

        // Authenticate user (consider switching to password-based authentication)
        val authentication: UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(req.username, req.password)

        SecurityContextHolder.getContext().authentication = authentication
        // Generate JWT token
        val token: String = jwtService.generateToken(user)
        return token;
    }

}