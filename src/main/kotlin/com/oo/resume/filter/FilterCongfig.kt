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
@Configuration
open class FilterCongfig {

    @Bean
    open fun registApiExcuteLogFilter(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.setFilter(ApiExcuteLog())
        registration.addUrlPatterns("/*")
        registration.setName(ApiExcuteLog::class.simpleName)
        registration.setOrder(ORDER_API_EXCUTE_LOG)
        return registration
    }

    companion object {
        private const val ORDER_API_EXCUTE_LOG = 1
    }

}