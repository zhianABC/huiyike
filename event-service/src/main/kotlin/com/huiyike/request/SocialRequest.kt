package com.huiyike.request

data class SocialRequest(
    var socialId: String?,
    var logo: String?,
    val fullName: String?,
    val abbrName: String?,
    val introduction: String?,
    val industry: String?,
    val creator: String?,
    var createTime: Long?,
    var updateTime: String?,
    var status: Int?,
    val remark: String?,
    val category: String?
)
