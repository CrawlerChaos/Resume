package com.oo.resume.httptrace

import org.springframework.boot.actuate.trace.http.Include
import org.springframework.http.HttpHeaders
import java.net.URI
import java.security.Principal
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier
import java.util.stream.Collectors

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-10-26 11:10
 */
class XHttpExchangeTracer
/**
 * Creates a new `HttpExchangeTracer` that will use the given `includes`
 * to determine the contents of its traces.
 *
 * @param includes the includes
 */
(private val includes: Set<Include>) {

    /**
     * Begins the tracing of the exchange that was initiated by the given `request`
     * being received.
     *
     * @param request the received request
     * @return the HTTP trace for theX
     */
    fun receivedRequest(request: XTraceableRequest): XHttpTrace {
        return XHttpTrace(FilteredTraceableRequest(request))
    }

    /**
     * Ends the tracing of the exchange that is being concluded by sending the given
     * `response`.
     *
     * @param trace     the trace for the exchange
     * @param response  the response that concludes the exchange
     * @param principal a supplier for the exchange's principal
     * @param sessionId a supplier for the id of the exchange's session
     */
    fun sendingResponse(trace: XHttpTrace, response: XTraceableResponse,
                        principal: Supplier<Principal>?, sessionId: Supplier<String>?) {
        setIfIncluded(Include.TIME_TAKEN,
                Supplier { System.currentTimeMillis() - trace.timestamp.toEpochMilli() },
                Consumer<Long> { trace.setTimeTaken(it) })
        setIfIncluded(Include.SESSION_ID, sessionId, Consumer { trace.setSessionId(it) })
        setIfIncluded(Include.PRINCIPAL, principal, Consumer { trace.setPrincipal(it) })
        trace.response = XHttpTrace.Response(FilteredTraceableResponse(response))
    }

    /**
     * Post-process the given mutable map of request `headers`.
     *
     * @param headers the headers to post-process
     */
    protected fun postProcessRequestHeaders(headers: Map<String, List<String>>) {

    }

    private fun <T> getIfIncluded(include: Include, valueSupplier: Supplier<T>): T? {
        return if (this.includes.contains(include)) valueSupplier.get() else null
    }

    private fun <T> setIfIncluded(include: Include, supplier: Supplier<T>?,
                                  consumer: Consumer<T>) {
        if (this.includes.contains(include) && supplier != null) {
            consumer.accept(supplier.get())
        }
    }

    private fun getHeadersIfIncluded(include: Include,
                                     headersSupplier: Supplier<Map<String, List<String>>>,
                                     headerPredicate: Predicate<String>): Map<String, List<String>> {
        return if (!this.includes.contains(include)) {
            LinkedHashMap()
        } else {
            headersSupplier.get().entries.stream()
                    .filter { entry -> headerPredicate.test(entry.key) }
                    .collect(Collectors.toMap<Map.Entry<String, List<String>>, String, List<String>>(Function<Map.Entry<String, List<String>>, String> { it.key }, Function<Map.Entry<String, List<String>>, List<String>> { it.value }))
        }
    }

    private inner class FilteredTraceableRequest constructor(private val delegate: XTraceableRequest) : XTraceableRequest {

        override fun getMethod(): String {
            return this.delegate.method
        }

        override fun getUri(): URI {
            return this.delegate.uri
        }

        override fun getHeaders(): Map<String, List<String>> {
            val headers = getHeadersIfIncluded(
                    Include.REQUEST_HEADERS, Supplier { this.delegate.headers },
                    Predicate { this.includedHeader(it) })
            postProcessRequestHeaders(headers)
            return headers
        }

        override fun getParams(): Map<String, Array<String>>? {
            return delegate.getParams()
        }

        override fun getBody(): Any? {
            return delegate.getBody()
        }

        private fun includedHeader(name: String): Boolean {
            if (name.equals(HttpHeaders.COOKIE, ignoreCase = true)) {
                return this@XHttpExchangeTracer.includes.contains(Include.COOKIE_HEADERS)
            }
            return if (name.equals(HttpHeaders.AUTHORIZATION, ignoreCase = true)) {
                this@XHttpExchangeTracer.includes
                        .contains(Include.AUTHORIZATION_HEADER)
            } else true
        }

        override fun getRemoteAddress(): String? {
            return getIfIncluded(Include.REMOTE_ADDRESS, Supplier { this.delegate.remoteAddress })
        }

    }


    private inner class FilteredTraceableResponse constructor(private val delegate: XTraceableResponse) : XTraceableResponse {

        override fun getStatus(): Int {
            return this.delegate.status
        }

        override fun getHeaders(): Map<String, List<String>> {
            return getHeadersIfIncluded(Include.RESPONSE_HEADERS,
                    Supplier { this.delegate.headers }, Predicate { this.includedHeader(it) })
        }

        override fun getBody(): Any? {
            return delegate.getBody()
        }

        private fun includedHeader(name: String): Boolean {
            return if (name.equals(HttpHeaders.SET_COOKIE, ignoreCase = true)) {
                this@XHttpExchangeTracer.includes.contains(Include.COOKIE_HEADERS)
            } else true
        }

    }
}
