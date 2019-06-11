package com.oo.resume.filter

import org.springframework.context.annotation.Configuration
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean


/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-07 15:28
 *
 */
class ApiExcuteLog : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request == null) return
        val start = System.currentTimeMillis()
        chain?.doFilter(request, response)
        val serveletRequest = request as HttpServletRequest
        println("request=${serveletRequest.getRequestURL()} " +
                "cost=${System.currentTimeMillis() - start}ms")
    }

}