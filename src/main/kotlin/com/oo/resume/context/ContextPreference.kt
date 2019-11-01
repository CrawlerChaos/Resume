package com.oo.resume.context

import com.oo.resume.entity.Account
import com.oo.resume.util.JsonUtil
import com.oo.resume.util.Logger
import kotlin.concurrent.getOrSet

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-07-20 16:17
 *
 */
class ContextPreference {

    companion object {

        private val threadLocal = ThreadLocal<HashMap<Context, Any>>()

        private fun get(): HashMap<Context, Any> {
            return threadLocal.getOrSet { HashMap<Context, Any>() }
        }

        fun put(key: Context?, value: Any?): Boolean {
//            Logger.info("\n------------put------------\n" + JsonUtil.pretty(value))
            if (key == null || value == null) return false
            get()[key] = value
            return true
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> get(key: Context?): T? {
            if (key == null) return null
            val value = get()[key] as T
//            Logger.info("\n------------get------------\n" + JsonUtil.pretty(value))
            return value
        }

        fun getAccount(): Account {
            return get(Context.Account)!!
        }

        fun putAccount(account: Account): Boolean {
            return put(Context.Account, account)
        }
    }
}