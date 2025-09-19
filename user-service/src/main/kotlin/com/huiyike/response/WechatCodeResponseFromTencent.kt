package com.huiyike.response

class WechatCodeResponseFromTencent (
    val openid: String? = null,
    val session_key: String? = null,
    val unionid: String? = null,
    val errcode: Int? = null,
    val errmsg: String? = null
)