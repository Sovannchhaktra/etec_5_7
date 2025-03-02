package com.spring.security.dto

data class UserReq(
    var username: String? = null,
    var password: String? = null,
    var gender: String? = null,
    var email: String? = null,
)
