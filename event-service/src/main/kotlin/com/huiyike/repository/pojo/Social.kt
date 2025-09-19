package com.huiyike.repository.pojo

data class Social(
    var socialId: String?,
    var logo: String?,
    val fullName: String?,
    val abbrName: String?,
    val introduction: String?,
    val industry: String?,
    val creator: String?,
    val createTime: Long?,
    val updateTime: String?,
    val status: Int?,
    val remark: String?,
    val category: String?
)
