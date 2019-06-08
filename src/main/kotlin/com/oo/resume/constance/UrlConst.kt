package com.oo.resume.constance

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-08 11:23
 *
 */
interface UrlConst {
    companion object {
        const val RESUME_PARAMS_RESUME_ID = "resumeId"
        const val RESUME_PARAMS_USER_ID = "userId"
        const val RESUME_PREFIX = "/resume"
        const val RESUME_INFO = "/{${RESUME_PARAMS_USER_ID}}"
        const val RESUME_DELETE = "/delete/{${RESUME_PARAMS_RESUME_ID}}"

        const val PREVIEW_PARAM_SHORT_LINK = "shortLink"
        const val REVIEW_PREFIX = "/review"
        const val REVIEW_RESUME = "/{${PREVIEW_PARAM_SHORT_LINK}}"

        const val USER_PREFIX = "/user"
        const val USER_REGIST = "/regist"
        const val USER_LOGIN = "/login"
        const val USER_UPDATE = "/update"
    }
}