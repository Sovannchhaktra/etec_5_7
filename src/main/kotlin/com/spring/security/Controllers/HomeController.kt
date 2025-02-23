package com.spring.security.Controllers

import com.spring.security.utils.Constant
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constant.BASE_URL + "/home")
class HomeController {
    @GetMapping(Constant.BASE_LIST)
    fun home(): String {
        return "Home"
    }
}