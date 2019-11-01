package com.oo.resume.config

import com.oo.resume.httptrace.XHttpExchangeTracer
import com.oo.resume.httptrace.XHttpTraceFilter
import com.oo.resume.httptrace.XHttpTraceRepository
import com.oo.resume.httptrace.XInMemoryHttpTraceRepository

import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-11-01 15:36
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = ["enabled"], matchIfMissing = true)
@EnableConfigurationProperties(HttpTraceProperties::class)
open class XHttpTraceConfig {

    //存储HttpTrace信息的对象
    @Bean
    @ConditionalOnMissingBean(XHttpTraceRepository::class)
    open fun traceRepository(): XInMemoryHttpTraceRepository {
        return XInMemoryHttpTraceRepository()
    }

    //创建HttpTrace对象Exchange
    @Bean
    @ConditionalOnMissingBean
    open fun httpExchangeTracer(traceProperties: HttpTraceProperties): XHttpExchangeTracer {
        return XHttpExchangeTracer(traceProperties.include)
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    internal class ServletTraceFilterConfiguration {
        //将我们自定义的Filter已Bean的方式注册，才能生效
        @Bean
        @ConditionalOnMissingBean
        fun httpTraceFilter(repository: XHttpTraceRepository,
                            tracer: XHttpExchangeTracer): XHttpTraceFilter {
            return XHttpTraceFilter(repository, tracer)
        }
    }

    //    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    //    static class ReactiveTraceFilterConfiguration {
    //
    //        @Bean
    //        @ConditionalOnMissingBean
    //        public HttpTraceWebFilter httpTraceWebFilter(HttpTraceRepository repository,
    //                                                     HttpExchangeTracer tracer, HttpTraceProperties traceProperties) {
    //            return new HttpTraceWebFilter(repository, tracer,
    //                    traceProperties.getInclude());
    //        }
    //    }
}