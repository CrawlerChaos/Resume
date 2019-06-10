package com.oo.resume.controller

import com.oo.resume.constance.ApiErrorMsg
import com.oo.resume.constance.Regex
import com.oo.resume.constance.UrlConst
import com.oo.resume.entity.*
import com.oo.resume.exception.ApiError
import com.oo.resume.exception.IlleageApiError
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.service.interf.IAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.regex.Pattern

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@RestController
@RequestMapping(UrlConst.USER_PREFIX)
class SignInController {

    @Autowired
    @Lazy
    lateinit var accountService: IAccountService

    @PostMapping(UrlConst.USER_REGIST)
    @Throws(ApiError::class)
    fun regist(@RequestBody registRequest: RegistRequest?): Account? {
        if (registRequest == null) throw IlleageApiError(ApiErrorMsg.NULL_REQUEST)
        if (registRequest.phone.isNullOrBlank()) throw IlleageApiError("电话不可为空")
        if (!Pattern.matches(Regex.MOBILE, registRequest.phone.trim())) throw IlleageApiError("电话不合法")
        if (registRequest.password.isNullOrBlank()) throw IlleageApiError("密码不可为空")
        if (registRequest.name.isNullOrBlank()) throw IlleageApiError("名字不可为空")
        if (accountService.getByPhone(registRequest.phone.trim()) != null) throw IlleageApiError("电话号码已存在")

        val user = Account(registRequest.phone.trim(), registRequest.password.trim(), registRequest.name.trim())
        updateSession(user)
        return accountService.save(user)
    }

    @PostMapping(UrlConst.USER_LOGIN)
    @Throws(ApiError::class)
    fun login(@RequestBody loginRequest: LoginRequest?): Account? {
        if (loginRequest == null) throw IlleageApiError(ApiErrorMsg.NULL_REQUEST)
        if (loginRequest.phone.isNullOrBlank()) throw IlleageApiError("电话不可为空")
        if (loginRequest.password.isNullOrBlank()) throw IlleageApiError("密码不可为空")

        val user = accountService.getByPhone(loginRequest.phone.trim())
        if (user == null || user.password != loginRequest.password) throw IlleageApiError("用户名或密码不正确")
        updateSession(user)
        return accountService.save(user)
    }


    @PutMapping(UrlConst.USER_UPDATE)
    @Throws(ApiError::class)
    fun update(@RequestBody pAccount: Account?): Account? {
        if (pAccount == null) throw IlleageApiError("请求参数为空")
        val user = accountService.getById(pAccount.id)
        if (user == null) throw IlleageApiError("用户不存在")
        return accountService.update(pAccount)
    }


    private fun updateSession(pAccount: Account) {
        pAccount.session_key = UUID.randomUUID().toString()
        pAccount.session_user = UUID.randomUUID().toString()
    }
}