package com.huiyike.logic

import com.huiyike.repository.pojo.Event
import com.huiyike.request.EventRequest
import com.huiyike.response.HuiyikeResponse
import com.huiyike.util.JsonMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class EventLogic {
    fun generateEventByRequest(request: EventRequest): Event {
        return Event(
            eventId = request.eventId,
            banner = request.banner,
            status = request.status,
            createTime = System.currentTimeMillis(),
            updateTime = System.currentTimeMillis(),
            creator = request.creator ?: "",
            remark = request.remark ?: "",
            category = request.category ?: "",
            industry = request.industry ?: "",
            city = request.city ?: "",
            country = request.country ?: "",
            courtName = request.courtName ?: "",
            courtLat = request.courtLat ?: "",
            courtLng = request.courtLng ?: "",
            detailAddr = request.detailAddr ?: "",
            square = request.square ?: "",
            startingDate = request.startingDate ?: 0,
            endDate = request.endDate ?: 0,
            fullName = request.fullName ?: "",
            abbrName = request.abbrName ?: "",
            introduction = request.introduction ?: "",
            detail = request.detail ?: "",
            socialId = request.socialId ?: ""
        )
    }

    fun generateEventResponse() : String {
        return JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.OK.value(),
            msg = "创建活动成功",
            data = "",
            err = ""
        ))
    }
}