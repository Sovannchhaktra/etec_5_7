package com.spring.security.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var role: String? = null,
)
