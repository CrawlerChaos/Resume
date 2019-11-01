package com.oo.resume.httptrace

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.boot.actuate.trace.http.HttpTrace
import org.springframework.context.annotation.Configuration
import org.springframework.util.Assert

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-10-26 11:40
 */

@Configuration
@Endpoint(id = "httptrace")
open class XHttpTraceEndpoint
/**
 * Create a new [org.springframework.boot.actuate.trace.http.HttpTraceEndpoint] instance.
 * @param repository the trace repository
 */
(private val repository: XHttpTraceRepository) {

    init {
        Assert.notNull(repository, "Repository must not be null")
    }

    @ReadOperation
    fun traces(): XHttpTraceEndpoint.HttpTraceDescriptor {
        return XHttpTraceEndpoint.HttpTraceDescriptor(this.repository.findAll())
    }

    /**
     * A description of an application's [HttpTrace] entries. Primarily intended for
     * serialization to JSON.
     */
    class HttpTraceDescriptor constructor(val traces: List<XHttpTrace>)

}


