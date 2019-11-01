package com.oo.resume.httptrace

import org.springframework.boot.actuate.trace.http.HttpTrace

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-10-26 11:02
 */
interface XHttpTraceRepository {
    /**
     * Find all [HttpTrace] objects contained in the repository.
     * @return the results
     */
    fun findAll(): List<XHttpTrace>

    /**
     * Adds a trace to the repository.
     * @param trace the trace to add
     */
    fun add(trace: XHttpTrace)
}
