package com.oo.resume.controller

import com.oo.resume.entity.*
import com.oo.resume.service.interf.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@Controller
class ErrorController {


    @GetMapping("/error")
    fun error(): String {
        return "error"
    }

}