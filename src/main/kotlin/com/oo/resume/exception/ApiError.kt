package com.oo.resume.exception

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-06 16:20
 *
 */
abstract class ApiError @JvmOverloads constructor(val msg: String? = null) : Exception(msg) {
    abstract fun code(): Int
}