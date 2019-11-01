package com.oo.resume.httptrace

import org.springframework.boot.actuate.trace.http.TraceableResponse

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-10-26 10:58
 */
interface XTraceableResponse : TraceableResponse {

    fun getBody(): Any?
}
