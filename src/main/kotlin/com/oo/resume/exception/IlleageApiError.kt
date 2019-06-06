package com.oo.resume.exception

import com.oo.resume.constance.ApiErrorCode

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 17:11
 *
 */
class IlleageApiError(msg: String?) : ApiError() {
    override fun code(): Int {
        return ApiErrorCode.ILLEGAL_REQUEST
    }
}