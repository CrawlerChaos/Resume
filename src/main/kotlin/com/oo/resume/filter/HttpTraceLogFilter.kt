package com.oo.resume.filter

import com.oo.resume.util.JsonUtil
import com.oo.resume.util.Logger
import io.micrometer.core.instrument.util.IOUtils
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-06-13 11:57
 */
class HttpTraceLogFilter : OncePerRequestFilter() {


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
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        val startTime = System.currentTimeMillis()
        try {
            filterChain.doFilter(request, response)
            status = response.getStatus()
        } finally {
            val path = request.getRequestURI()
            val requestBody = IOUtils.toString(request.getInputStream(), Charsets.UTF_8)
            Logger.info(requestBody)
            //1. 记录日志
            val traceLog = HttpTraceLog()
            traceLog.path = path
            traceLog.method = request.getMethod()
            val latency = System.currentTimeMillis() - startTime
            traceLog.timeTaken = latency
            traceLog.time = LocalDateTime.now().toString()
            traceLog.parameterMap = JsonUtil.generate(request.getParameterMap())
            traceLog.status = status
            Logger.info("\n------------Execute------------\n" + JsonUtil.pretty(traceLog))
            Logger.info("\n------------Request Body------------\n" + JsonUtil.pretty(getRequestBody(request)))
            Logger.info("\n------------Response Body------------\n" + JsonUtil.pretty(getResponseBody(response)))
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

    private fun getRequestBody(request: HttpServletRequest): String {
        var requestBody = ""
        val wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper::class.java)
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.inputStream)
            } catch (e: Throwable) {
                // NOOP
            }

        }
        return requestBody
    }

    private fun getResponseBody(response: HttpServletResponse): String {
        var responseBody = ""
        val wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.contentInputStream)
            } catch (e: Throwable) {
                // NOOP
            }

        }
        return responseBody
    }

    @Throws(IOException::class)
    private fun updateResponse(response: HttpServletResponse) {
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)?.copyBodyToResponse()
    }

    private data class HttpTraceLog(

            var path: String? = null,
            var parameterMap: String? = null,
            var method: String? = null,
            var timeTaken: Long? = null,
            var time: String? = null,
            var status: Int? = null
    )

}
