package com.oo.resume.handler

import com.oo.resume.constance.ApiErrorCode
import com.oo.resume.exception.ApiError
import com.oo.resume.param.response.ErrorBody
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletResponse

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 15:58
 *
 */
@RestControllerAdvice
class ErrorHandler {

    /**
     * 统一处理的Exception
     */
    @ExceptionHandler(Exception::class)
    fun handleError(response: HttpServletResponse, exception: Exception): ErrorBody {
        exception.printStackTrace()
        response.status = ApiErrorCode.SERVER_EXCEPTION
        return ErrorBody(ApiErrorCode.SERVER_EXCEPTION, "服务器内部错误")
    }

    /**
     * 统一处理api的Exception
     */
    @ExceptionHandler(ApiError::class)
    fun handleApiErrpr(apiError: ApiError, response: HttpServletResponse, exception: Exception): ErrorBody {
        response.status = apiError.code()
        return apiError.body
    }
}