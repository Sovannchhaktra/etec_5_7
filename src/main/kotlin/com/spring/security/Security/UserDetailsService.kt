package com.spring.security.Security

import com.spring.security.entity.Users
import com.spring.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService: UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")
        return MyUserDetails(user)
    }

}