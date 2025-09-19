package com.huiyike.repository.pojo

data class Account(
    val userId : String,
    val username: String,
    val salt : String,
    val password: String,
    val createDate: Long,
    val updateDate: Long
)
