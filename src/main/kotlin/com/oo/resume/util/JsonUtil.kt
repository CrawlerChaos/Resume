package com.oo.resume.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.micrometer.core.instrument.util.JsonUtils
import java.io.IOException


/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-13 13:20
 *
 */
object JsonUtil {
    private val mapper = ObjectMapper()

    /**
     * Serialize any Java value as a String.
     */
    @Throws(JsonProcessingException::class)
    fun generate(any: Any?): String? {
        if (any == null) return null
        return mapper.writeValueAsString(any)
    }

    /**
     * Deserialize JSON content from given JSON content String.
     */
    @Throws(IOException::class)
    fun <T> parse(content: String?, valueType: Class<T>?): T? {
        if (content == null || valueType == null) return null
        return mapper.readValue(content, valueType)
    }

    @Throws(JsonProcessingException::class)
    fun pretty(any: Any?): String? {
        val json = generate(any)
        if (json == null) return null
        return JsonUtils.prettyPrint(json)
    }
}