package com.oo.resume.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import javax.servlet.Filter


/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-07 15:28
 *
 */
@Configuration
open class FilterCongfig {


    @Bean
    open fun registHttpTraceLogFilter(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.setFilter(HttpTraceLogFilter())
        registration.addUrlPatterns("/*")
        registration.setName(HttpTraceLogFilter::class.simpleName)
        registration.setOrder(HTTP_TRACE_LOG)
        return registration
    }

    companion object {
        private const val HTTP_TRACE_LOG = Ordered.LOWEST_PRECEDENCE - 20
    }

}