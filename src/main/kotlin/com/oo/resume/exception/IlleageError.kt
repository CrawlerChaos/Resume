package com.oo.resume.exception

import com.oo.resume.data.const.ApiErrorCode

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 17:11
 *
 */
class IlleageError(msg: String?) : ApiError(msg) {
    override fun code(): Int {
        return ApiErrorCode.ILLEGAL_REQUEST
    }
}