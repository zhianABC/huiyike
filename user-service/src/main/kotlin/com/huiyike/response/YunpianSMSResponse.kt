package com.huiyike.response

data class YunpianSMSResponse(
    val code: Int,
    val msg: String,
    val count: Int,
    val fee: Double,
    val unit: String,
    val mobile: String,
    val sid: Long
)