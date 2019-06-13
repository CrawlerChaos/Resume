package com.oo.resume.util

import org.modelmapper.ModelMapper
import java.lang.reflect.Type

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-13 10:44
 *
 */
object BeanCovertor {
    private val mapper: ModelMapper

    init {
        mapper = ModelMapper()
    }

    fun <I, O> convert(i: I?, type: Type?): O? {
        if (i == null || type == null) return null
        try {
            return mapper.map(i, type)
        } catch (exception: Throwable) {
            exception.printStackTrace()
            return null
        }
    }

}