package com.spring.security.Controllers

import com.spring.security.dto.UserLogin
import com.spring.security.dto.UserReq
import com.spring.security.services.AuthService
import com.spring.security.utils.Constant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.BASE_URL + "/users")
class UserController {
    @Autowired
    lateinit var authService: AuthService

    @PostMapping("/register")
    fun create(@RequestBody req: UserReq): String {
        return authService.createUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: UserLogin): String {
        return authService.login(req)
    }
}