package com.huiyike.exception

class EventServiceException (message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}