package com.oo.resume.httptrace

import org.springframework.boot.actuate.trace.http.TraceableRequest

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-10-26 10:55
 */
interface XTraceableRequest : TraceableRequest {
    /**
     * Returns the method (GET, POST, etc) of the request.
     * @return the method
     */

    fun getParams(): Map<String, Array<String>>?

    fun getBody(): Any?

    /**
     * Returns the remote address from which the request was sent, if available.
     * @return the remote address or `null`
     */
}
