package com.oo.resume.exception

import com.oo.resume.param.response.ErrorBody

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 16:20
 *
 */
abstract class ApiError constructor(msg: String?) : Exception(msg) {

    var body: ErrorBody

    init {
        body = ErrorBody(code(), msg)
    }

    abstract fun code(): Int

}