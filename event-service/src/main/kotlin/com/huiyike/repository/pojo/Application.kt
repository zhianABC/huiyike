package com.huiyike.repository.pojo

data class Application (
    val appId: String,
    val secret: String,
    val socialId: String,
    val createDate: Long,
    val updateDate: Long
)