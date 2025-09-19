package com.huiyike.logic

import com.huiyike.response.HuiyikeResponse
import com.huiyike.util.JsonMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class EventImageLogic {
    fun generateExchangeImageResponse(imagePath: String): String {
        return JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.OK.value(),
            msg = "图片更换URL成功",
            data = imagePath,
            err = ""
        ))
    }
}