package com.oo.resume.util

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-07-20 16:39
 *
 */
object Logger : Log {

    private val log = LogFactory.getLog(Logger::class.java.simpleName)

    override fun warn(message: Any?) {
        log.warn(message)
    }

    override fun warn(message: Any?, t: Throwable?) {
        log.warn(message, t)
    }

    override fun fatal(message: Any?) {
        log.fatal(message)
    }

    override fun fatal(message: Any?, t: Throwable?) {
        log.fatal(message, t)
    }

    override fun info(message: Any?) {
        log.info(message)
    }

    override fun info(message: Any?, t: Throwable?) {
        log.info(message, t)
    }


    override fun error(message: Any?) {
        log.error(message)
    }

    override fun error(message: Any?, t: Throwable?) {
        log.error(message, t)
    }

    override fun debug(message: Any?) {
        log.debug(message)
    }

    override fun debug(message: Any?, t: Throwable?) {
        log.debug(message, t)
    }

    override fun trace(message: Any?) {
        log.trace(message)
    }

    override fun trace(message: Any?, t: Throwable?) {
        log.trace(message, t)
    }

    override fun isTraceEnabled(): Boolean {
        return log.isTraceEnabled
    }

    override fun isDebugEnabled(): Boolean {
        return log.isDebugEnabled
    }

    override fun isFatalEnabled(): Boolean {
        return log.isFatalEnabled
    }

    override fun isErrorEnabled(): Boolean {
        return log.isErrorEnabled
    }

    override fun isInfoEnabled(): Boolean {
        return log.isInfoEnabled
    }

    override fun isWarnEnabled(): Boolean {
        return log.isWarnEnabled
    }
}

