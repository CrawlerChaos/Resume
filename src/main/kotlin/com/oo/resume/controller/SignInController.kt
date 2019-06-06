package com.oo.resume.controller

import com.oo.resume.constance.ApiErrorMsg
import com.oo.resume.entity.*
import com.oo.resume.exception.ApiError
import com.oo.resume.exception.IlleageApiError
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.service.interf.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@RestController
@RequestMapping("/user")
class SignInController {

    @Autowired
    @Lazy
    lateinit var userService: IUserService

    @PostMapping("/regist")
    @Throws(ApiError::class)
    fun regist(@RequestBody registRequest: RegistRequest?): User? {
        if (registRequest == null) throw IlleageApiError(ApiErrorMsg.NULL_REQUEST)
        if (registRequest.phone.isNullOrBlank()) throw IlleageApiError("电话不可为空")
        if (registRequest.password.isNullOrBlank()) throw IlleageApiError("密码不可为空")
        if (registRequest.name.isNullOrBlank()) throw IlleageApiError("名字不可为空")
        if (userService.getByPhone(registRequest.phone.trim()) != null) throw IlleageApiError("电话号码已存在")

        val user = User(registRequest.phone.trim(), registRequest.password.trim(), registRequest.name.trim())
        updateSession(user)
        return userService.save(user)
    }

    @PostMapping("/login")
    @Throws(ApiError::class)
    fun login(@RequestBody loginRequest: LoginRequest?): User? {
        if (loginRequest == null) throw IlleageApiError(ApiErrorMsg.NULL_REQUEST)
        if (loginRequest.phone.isNullOrBlank()) throw IlleageApiError("电话不可为空")
        if (loginRequest.password.isNullOrBlank()) throw IlleageApiError("密码不可为空")

        val user = userService.getByPhone(loginRequest.phone.trim())
        if (user == null || user.password != loginRequest.password) throw IlleageApiError("用户名或密码不正确")
        updateSession(user)
        return userService.save(user)
    }


    @PutMapping("/update")
    @Throws(ApiError::class)
    fun update(@RequestBody pUser: User?): User? {
        if (pUser == null) throw IlleageApiError("请求参数为空")
        val user = userService.getById(pUser.id)
        if (user == null) throw IlleageApiError("用户不存在")
        return userService.update(pUser)
    }


    private fun updateSession(pUser: User) {
        pUser.session_key = UUID.randomUUID().toString()
        pUser.session_user = UUID.randomUUID().toString()
    }
}