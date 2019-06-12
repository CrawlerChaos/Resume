package com.oo.resume

import com.oo.resume.constance.UrlConst
import com.oo.resume.interceptor.SessionInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-11 13:46
 *
 */
@Configuration
open class WebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(SessionInterceptor()).addPathPatterns(
//                "${UrlConst.RESUME_PREFIX}/**", UrlConst.ACCOUNT_PREFIX + UrlConst.ACCOUNT_UPDATE)
        super.addInterceptors(registry)
    }
}
