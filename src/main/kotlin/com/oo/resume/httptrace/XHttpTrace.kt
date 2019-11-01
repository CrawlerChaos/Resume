/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oo.resume.httptrace

import org.springframework.util.StringUtils
import java.net.URI
import java.time.Instant

/**
 * A trace event for handling of an HTTP request and response exchange. Can be used for
 * analyzing contextual information such as HTTP headers.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @since 2.0.0
 */
class XHttpTrace {

    val timestamp: Instant

    @Volatile
    var principal: Principal? = null
        private set

    @Volatile
    var session: Session? = null
        private set

    val request: Request

    @Volatile
    var response: Response? = null
        internal set

    @Volatile
    var timeTaken: Long? = null
        private set

    /**
     * Creates a fully-configured `HttpTrace` instance. Primarily for use by
     * [XInMemoryHttpTraceRepository] implementations when recreating a trace from a
     * persistent store.
     *
     * @param request   the request
     * @param response  the response
     * @param timestamp the timestamp of the request-response exchange
     * @param principal the principal, if any
     * @param session   the session, if any
     * @param timeTaken the time taken, in milliseconds, to complete the request-response
     * exchange, if known
     * @since 2.1.0
     */
    constructor(request: Request, response: Response, timestamp: Instant,
                principal: Principal, session: Session, timeTaken: Long?) {
        this.request = request
        this.response = response
        this.timestamp = timestamp
        this.principal = principal
        this.session = session
        this.timeTaken = timeTaken
    }

    internal constructor(request: XTraceableRequest) {
        this.request = Request(request)
        this.timestamp = Instant.now()
    }

    internal fun setPrincipal(principal: java.security.Principal?) {
        if (principal != null) {
            this.principal = Principal(principal.name)
        }
    }

    internal fun setSessionId(sessionId: String) {
        if (StringUtils.hasText(sessionId)) {
            this.session = Session(sessionId)
        }
    }

    internal fun setTimeTaken(timeTaken: Long) {
        this.timeTaken = timeTaken
    }

    /**
     * Trace of an HTTP request.
     */
    class Request
    /**
     * Creates a fully-configured `Request` instance. Primarily for use by
     * [XInMemoryHttpTraceRepository] implementations when recreating a request from a
     * persistent store.
     *
     * @param method        the HTTP method of the request
     * @param uri           the URI of the request
     * @param headers       the request headers
     * @param remoteAddress remote address from which the request was sent, if known
     * @since 2.1.0
     */
    (val method: String?, val uri: URI?, val headers: Map<String, List<String>>?,
     val params: Map<String, Array<String>>?, val body: Any?, val remoteAddress: String?) {


        constructor(request: XTraceableRequest) : this(request.method, request.uri, request.headers,
                request.getParams(), request.getBody(), request.remoteAddress) {
        }


    }

    /**
     * Trace of an HTTP response.
     */
    class Response
    /**
     * Creates a fully-configured `Response` instance. Primarily for use by
     * [XInMemoryHttpTraceRepository] implementations when recreating a response from a
     * persistent store.
     *
     * @param status  the status of the response
     * @param headers the response headers
     * @since 2.1.0
     */
    (val status: Int?, val headers: Map<String, List<String>>?, val body: Any?) {


        internal constructor(response: XTraceableResponse) : this(response.status, response.headers, response.getBody()) {}


    }

    /**
     * Session associated with an HTTP request-response exchange.
     */
    class Session
    /**
     * Creates a `Session`.
     *
     * @param id the session id
     * @since 2.1.0
     */
    (val id: String)

    /**
     * Principal associated with an HTTP request-response exchange.
     */
    class Principal
    /**
     * Creates a `Principal`.
     *
     * @param name the name of the principal
     * @since 2.1.0
     */
    (val name: String)

}
