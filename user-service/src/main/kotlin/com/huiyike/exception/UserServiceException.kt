package com.huiyike.exception

class UserServiceException (message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}