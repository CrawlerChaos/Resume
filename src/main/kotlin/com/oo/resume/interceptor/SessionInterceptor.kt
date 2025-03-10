package com.oo.resume.interceptor

import com.oo.resume.context.Context
import com.oo.resume.context.ContextPreference
import com.oo.resume.data.header.HeaderConst
import com.oo.resume.exception.AuthorityError
import com.oo.resume.service.interf.IAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-07 15:17
 *
 */
@Component
class SessionInterceptor() : HandlerInterceptor {

    @Autowired
    lateinit var accountService: IAccountService

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        val session_user = request?.getHeader(HeaderConst.SESSION_USER)
        val session_key = request?.getHeader(HeaderConst.SESSION_KEY)
        if (session_user == null || session_key == null) throw AuthorityError()
        val account = accountService.getBySessionUser(session_user)
        if (account == null) throw AuthorityError()
        ContextPreference.put(Context.Account, account)
        val session_key_real = account.session_key
        if (session_key_real == null || session_key_real != session_key) throw AuthorityError()
        return true
    }

}