package com.oo.resume.controller

import com.oo.resume.constance.ApiErrorMsg
import com.oo.resume.constance.Regex
import com.oo.resume.entity.Account
import com.oo.resume.exception.ApiError
import com.oo.resume.exception.AuthorityError
import com.oo.resume.exception.IlleageError
import com.oo.resume.param.header.HeaderConst
import com.oo.resume.param.path.UrlConst
import com.oo.resume.param.request.LoginRequest
import com.oo.resume.param.request.RegistRequest
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.service.interf.IAccountService
import com.oo.resume.util.BeanCovertor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders
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
@RequestMapping(UrlConst.ACCOUNT_PREFIX)
class SignInController {

    @Autowired
    @Lazy
    lateinit var accountService: IAccountService

    @PostMapping(UrlConst.ACCOUNT_REGIST)
    @Throws(ApiError::class)
    fun regist(@RequestBody registRequest: RegistRequest?): AccountDTO? {
        if (registRequest == null) throw IlleageError(ApiErrorMsg.NULL_REQUEST)
        if (registRequest.phone.isNullOrBlank()) throw IlleageError("电话不可为空")
        if (!Pattern.matches(Regex.MOBILE, registRequest.phone.trim())) throw IlleageError("电话不合法")
        if (registRequest.password.isNullOrBlank()) throw IlleageError("密码不可为空")
        if (registRequest.name.isNullOrBlank()) throw IlleageError("名字不可为空")
        if (accountService.getByPhone(registRequest.phone.trim()) != null) throw IlleageError("电话号码已存在")

        val account = Account(registRequest.phone.trim(), registRequest.password.trim(), registRequest.name.trim())
        updateSessionUser(account)
        updateSessionKey(account)
        return BeanCovertor.convert(accountService.save(account), AccountDTO::class.java)
    }

    @PostMapping(UrlConst.ACCOUNT_LOGIN)
    @Throws(ApiError::class)
    fun login(@RequestBody loginRequest: LoginRequest?): AccountDTO? {
        if (loginRequest == null) throw IlleageError(ApiErrorMsg.NULL_REQUEST)
        if (loginRequest.phone.isNullOrBlank()) throw IlleageError("电话不可为空")
        if (loginRequest.password.isNullOrBlank()) throw IlleageError("密码不可为空")

        val account = accountService.getByPhone(loginRequest.phone.trim())
        if (account == null || account.password != loginRequest.password) throw IlleageError("用户名或密码不正确")
        updateSessionKey(account)
        return BeanCovertor.convert(accountService.save(account), AccountDTO::class.java)
    }


    @PutMapping(UrlConst.ACCOUNT_UPDATE)
    @Throws(ApiError::class)
    fun update(@RequestBody pAccount: Account?, @RequestHeader headers: HttpHeaders): AccountDTO? {
        val user_session = headers[HeaderConst.SESSION_USER]?.getOrNull(0)
        if (user_session == null) throw AuthorityError()
        if (pAccount == null) throw IlleageError("请求参数为空")
        val account = accountService.getBySessionUser(user_session)
        if (account == null) throw IlleageError("用户不存在")
        BeanCovertor.copyProperties(pAccount, account, true, "id")
        return BeanCovertor.convert(accountService.save(account), AccountDTO::class.java)
    }

    private fun updateSessionUser(pAccount: Account) {
        pAccount.session_key = UUID.randomUUID().toString()
    }

    private fun updateSessionKey(pAccount: Account) {
        pAccount.session_key = UUID.randomUUID().toString()
    }


}