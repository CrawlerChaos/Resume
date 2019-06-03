package com.oo.resume.controller

import com.oo.resume.entity.*
import com.oo.resume.service.interf.IUserService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    @Lazy
    lateinit var userService: IUserService

    @PostMapping("/regist")
    fun regist(@RequestBody phone: String?): User? {
        if (phone == null) return null
        val user = User(phone, "123456", "haha")
        return userService.save(user)
    }


    @PutMapping("/update")
    fun update(@RequestBody user: User?): User? {
        if (user == null) return user
        return userService.update(user)
    }

}