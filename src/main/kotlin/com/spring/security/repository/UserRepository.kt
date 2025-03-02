package com.spring.security.repository

import com.spring.security.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<Users, Long> {
    fun findByUsername(username: String): Users?
}