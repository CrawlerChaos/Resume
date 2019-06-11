package com.oo.resume.interceptor

import com.oo.resume.exception.AuthorityApiError
import com.oo.resume.param.header.HeaderConst
import com.oo.resume.service.interf.IAccountService
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-07 15:17
 *
 */
class SessionInterceptor(private val accountService: IAccountService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        val session_user = request?.getHeader(HeaderConst.SESSION_USER)
        val session_key = request?.getHeader(HeaderConst.SESSION_KEY)
        if (session_user == null || session_key == null) throw AuthorityApiError()
        val session_key_real = accountService.getBySessionUser(session_user)?.session_key
        if (session_key_real == null || session_key_real != session_key) throw AuthorityApiError()
        return true
    }

}