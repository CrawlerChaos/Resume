package com.oo.resume.exception

import com.oo.resume.constance.ApiErrorCode

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 17:11
 *
 */
class AuthorityError @JvmOverloads constructor(msg: String = "无权限") : ApiError(msg) {
    override fun code(): Int {
        return ApiErrorCode.UN_AUTHORITY
    }
}