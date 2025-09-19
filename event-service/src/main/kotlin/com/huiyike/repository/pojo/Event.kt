package com.huiyike.repository.pojo

data class Event(
    val eventId: String?,
    val banner: String?,
    val fullName: String?,
    val abbrName: String?,
    val introduction: String?,
    val detail: String?,
    val category: String?,
    val industry: String?,
    val city: String?,
    val country: String?,
    val courtName: String?,
    val courtLat: String?,
    val courtLng: String?,
    val detailAddr: String?,
    val square: String?,
    val startingDate: Int?,
    val endDate: Int?,
    val createTime: Long?,
    val updateTime: Long?,
    val creator: String?,
    val status: Int?,
    val remark: String?,
    val socialId: String?
)
