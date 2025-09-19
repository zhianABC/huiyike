package com.huiyike.util

import java.util.UUID

object UUIdGenerator {
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}