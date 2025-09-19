package com.huiyike.exception

class UserInvalidException (message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}