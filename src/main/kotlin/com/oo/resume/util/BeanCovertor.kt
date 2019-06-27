package com.oo.resume.util

import org.modelmapper.ModelMapper
import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapperImpl
import java.lang.reflect.Type
import java.util.*


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

    @JvmOverloads
    fun <Source, Target> copyProperties(source: Source?, target: Target?, ignoreNull: Boolean = true, vararg ignoreProperties: String) {
        if (source == null || target == null) return
        if (ignoreNull) BeanUtils.copyProperties(source, target, *getNullPropertyNames(source).plus(ignoreProperties))
        else BeanUtils.copyProperties(source, target, *ignoreProperties)
    }

    private fun getNullPropertyNames(source: Any): Array<String> {
        val src = BeanWrapperImpl(source)
        val pds = src.propertyDescriptors

        val emptyNames = HashSet<String>()
        for (pd in pds) {
            val srcValue = src.getPropertyValue(pd.name)
            if (srcValue == null) emptyNames.add(pd.name)
        }
        val result = arrayOfNulls<String>(emptyNames.size)
        return emptyNames.toArray(result)
    }

}