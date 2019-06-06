package com.oo.resume.handler

import com.oo.resume.constance.PageConst
import com.oo.resume.exception.ApiError
import org.hibernate.annotations.common.util.impl.`Log_$logger`
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.HashMap
import javax.servlet.http.HttpServletResponse

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 15:58
 *
 */
@ControllerAdvice
class ErrorHandler {

    /**
     * 统一处理h5的Exception
     */
    @ExceptionHandler(Exception::class)
    fun handleError(): String {
        return PageConst.PAGE_ERROR
    }

    /**
     * 统一处理api的Exception
     */
    @ExceptionHandler(ApiError::class)
    @ResponseBody
    fun handleApiErrpr(apiError: ApiError, response: HttpServletResponse, exception: Exception): Map<String, Any?> {
        exception.printStackTrace()
        response.status = apiError.code()
        return sortedMapOf(
                "code" to apiError.code(),
                "msg" to apiError.msg
        )
    }
}