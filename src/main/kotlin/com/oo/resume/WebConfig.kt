package com.oo.resume

import com.oo.resume.data.path.AccountUrl
import com.oo.resume.data.path.ResumeUrl
import com.oo.resume.interceptor.SessionInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
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

    @Bean
    open fun createSessionInterceptor(): SessionInterceptor {
        return SessionInterceptor()
    }

    @Bean
    open fun responseBodyConverter(): HttpMessageConverter<String> {
        return StringHttpMessageConverter(Charsets.UTF_8)
    }


    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(createSessionInterceptor()).addPathPatterns(
                "${ResumeUrl.PREFIX}/**",
                AccountUrl.PREFIX + AccountUrl.PATH_UPDATE,
                AccountUrl.PREFIX + AccountUrl.PATH_RESET_PASSWORD)
        super.addInterceptors(registry)
    }
}
