package com.oo.resume.controller

import com.oo.resume.constance.ApiErrorMsg
import com.oo.resume.constance.Regex
import com.oo.resume.context.ContextPreference
import com.oo.resume.data.path.UrlConst
import com.oo.resume.data.request.LoginRequest
import com.oo.resume.data.request.RegistRequest
import com.oo.resume.data.response.AccountDTO
import com.oo.resume.entity.Account
import com.oo.resume.exception.ApiError
import com.oo.resume.exception.IlleageError
import com.oo.resume.service.interf.IAccountService
import com.oo.resume.util.BeanCovertor
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
@RequestMapping(UrlConst.ACCOUNT_PREFIX)
class SignInController {

    @Autowired
    @Lazy
    lateinit var accountService: IAccountService

    @PostMapping(UrlConst.ACCOUNT_REGIST)
    @Throws(ApiError::class)
    fun regist(@RequestBody registRequest: RegistRequest?): AccountDTO? {
        if (registRequest == null) throw IlleageError(ApiErrorMsg.NULL_REQUEST)
        val phone = registRequest.phone
        if (phone.isNullOrBlank()) throw IlleageError("电话不可为空")
        if (!Pattern.matches(Regex.MOBILE, phone.trim())) throw IlleageError("电话不合法")
        val password = registRequest.password
        if (password.isNullOrBlank()) throw IlleageError("密码不可为空")
        val name = registRequest.name
        if (name.isNullOrBlank()) throw IlleageError("名字不可为空")
        if (Objects.nonNull(accountService.getByPhone(phone.trim()))) throw IlleageError("电话号码已存在")

        val account = Account(phone.trim(), password.trim(), name.trim())
        updateSessionUser(account)
        updateSessionKey(account)
        return BeanCovertor.convert(accountService.save(account), AccountDTO::class.java)
    }

    @PostMapping(UrlConst.ACCOUNT_LOGIN)
    @Throws(ApiError::class)
    fun login(@RequestBody loginRequest: LoginRequest?): AccountDTO? {
        if (loginRequest == null) throw IlleageError(ApiErrorMsg.NULL_REQUEST)
        val phone = loginRequest.phone
        if (phone.isNullOrBlank()) throw IlleageError("电话不可为空")
        val password = loginRequest.password
        if (password.isNullOrBlank()) throw IlleageError("密码不可为空")
        val account = accountService.getByPhone(phone.trim())
        if (account == null || account.password != password) throw IlleageError("用户名或密码不正确")
        updateSessionKey(account)
        return BeanCovertor.convert(accountService.save(account), AccountDTO::class.java)
    }


    @PutMapping(UrlConst.ACCOUNT_UPDATE)
    @Throws(ApiError::class)
    fun update(@RequestBody pAccount: Account?): AccountDTO? {
        if (pAccount == null) throw IlleageError("请求参数为空")
        var account = ContextPreference.getAccount()
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