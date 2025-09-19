package com.huiyike.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonMapper {
    private val mapper = jacksonObjectMapper()

    fun toJsonString(obj: Any): String {
        return try {
            mapper.writeValueAsString(obj).replace("\n", "").replace("\r", "")
        } catch (e: Exception) {
            throw RuntimeException("Error converting object to JSON string", e)
        }
    }

    fun <T> fromJsonString(json: String, clazz: Class<T>): T {
        return try {
            mapper.readValue(json, clazz)
        } catch (e: Exception) {
            throw RuntimeException("Error converting JSON string to object", e)
        }
    }
}