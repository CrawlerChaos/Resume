package com.oo.resume.httptrace

import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.UriUtils
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-06-13 11:57
 */
class XHttpTraceFilter : OncePerRequestFilter {

    //    存储HttpTrace的repository,默认是居于内存的，可扩展该类跟换存储数据的方式
    private var httpTraceRepository: XHttpTraceRepository
    //该类创建HttpTrace对象，Set<Include>包含的内容是我们需要展示那些内容的容器(request-headers,response-headers,remote-address,time-taken)
    private var httpExchangeTracer: XHttpExchangeTracer

    constructor(httpTraceRepository: XHttpTraceRepository, httpExchangeTracer: XHttpExchangeTracer) {
        this.httpTraceRepository = httpTraceRepository;
        this.httpExchangeTracer = httpExchangeTracer;
    }


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(requestOriginal: HttpServletRequest, responseOriginal: HttpServletResponse, filterChain: FilterChain) {
        var request = requestOriginal
        var response = responseOriginal
        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response)
            return
        }
        if (request !is ContentCachingRequestWrapper) {
            request = ContentCachingRequestWrapper(request)
        }
        if (response !is ContentCachingResponseWrapper) {
            response = ContentCachingResponseWrapper(response)
        }
        try {
            filterChain.doFilter(request, response)
        } finally {
            val isActuatorUri = isActuatorUri(request.getRequestURI())
            val displayBody = !isActuatorUri && isJsonBody(request)
            var httpTrace: XHttpTrace? = httpExchangeTracer.receivedRequest(TraceRequsetWrapper(request, displayBody))
            if (httpTrace == null) return
            httpExchangeTracer.sendingResponse(httpTrace, TraceResponseWrapper(response, displayBody), null, null)
            if (!isActuatorUri) httpTraceRepository.add(httpTrace)
            updateResponse(response)
        }
    }

    private fun isRequestValid(request: HttpServletRequest): Boolean {
        try {
            URI(request.requestURL.toString())
            return true
        } catch (ex: URISyntaxException) {
            return false
        }

    }

    private fun isJsonBody(request: HttpServletRequest?): Boolean {
        return request?.getContentType()?.toLowerCase() == "application/json"
    }


    @Throws(IOException::class)
    private fun updateResponse(response: HttpServletResponse) {
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)?.copyBodyToResponse()
    }


    private class TraceRequsetWrapper(val request: HttpServletRequest, val displayBody: Boolean) : XTraceableRequest {
        override fun getBody(): Any? {
            if (displayBody) return getRequestBody(request)
            return null
        }

        override fun getParams(): MutableMap<String, Array<String>> {
            return request.getParameterMap()
        }

        override fun getHeaders(): MutableMap<String, MutableList<String>> {
            return extractHeaders()
        }

        private fun extractHeaders(): MutableMap<String, MutableList<String>> {
            val headers = LinkedHashMap<String, MutableList<String>>()
            val names = this.request.headerNames
            while (names.hasMoreElements()) {
                val name = names.nextElement()
                headers[name] = Collections.list(this.request.getHeaders(name))
            }
            return headers
        }

        override fun getMethod(): String {
            return request.getMethod()
        }

        override fun getUri(): URI {
            val queryString = this.request.queryString
            if (!StringUtils.hasText(queryString)) {
                return URI.create(this.request.requestURL.toString())
            }
            try {
                val urlBuffer = appendQueryString(queryString)
                return URI(urlBuffer.toString())
            } catch (ex: URISyntaxException) {
                val encoded = UriUtils.encodeQuery(queryString, StandardCharsets.UTF_8)
                val urlBuffer = appendQueryString(encoded)
                return URI.create(urlBuffer.toString())
            }

        }

        private fun appendQueryString(queryString: String): StringBuffer {
            return this.request.requestURL.append("?").append(queryString)
        }

        override fun getRemoteAddress(): String {
            return request.getRemoteAddr()
        }

        private fun getRequestBody(request: HttpServletRequest): Any? {
            var requestBody: Any? = null
            val wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper::class.java)
            if (wrapper != null) {
                try {
                    requestBody = streamToObject(wrapper.inputStream)
                } catch (e: Throwable) {
                    // NOOP
                }

            }
            return requestBody
        }
    }

    private class TraceResponseWrapper(val response: HttpServletResponse, val displayBody: Boolean) : XTraceableResponse {
        override fun getBody(): Any? {
            if (displayBody) return getResponseBody(response)
            return null
        }

        override fun getHeaders(): MutableMap<String, MutableList<String>>? {
            return extractHeaders()
        }

        override fun getStatus(): Int {
            return response.status
        }

        private fun extractHeaders(): MutableMap<String, MutableList<String>> {
            val headers = linkedMapOf<String, MutableList<String>>()
            response.headerNames.forEach {
                if (it != null) headers[it] = arrayListOf(response.getHeader(it))
            }
            return headers
        }


        private fun getResponseBody(response: HttpServletResponse): Any? {
            var responseBody: Any? = null
            val wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)
            if (wrapper != null) {
                try {
                    responseBody = streamToObject(wrapper.contentInputStream)
                } catch (e: Throwable) {
                    // NOOP
                }

            }
            return responseBody
        }
    }


    companion object {
        fun streamToObject(inputStream: InputStream?): Any? {
            val content = inputStream?.bufferedReader(Charsets.UTF_8)?.readText()
            return content
        }
    }

    private fun isActuatorUri(requestUri: String): Boolean {
        val matcher = AntPathMatcher()
        return matcher.match("/actuator/**", requestUri)
    }

}
