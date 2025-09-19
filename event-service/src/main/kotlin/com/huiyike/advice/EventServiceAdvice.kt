package com.huiyike.advice

import com.huiyike.exception.EventServiceException
import com.huiyike.response.HuiyikeResponse
import com.huiyike.util.JsonMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EventServiceAdvice {
    @ExceptionHandler(EventServiceException::class)
    fun handleEventServiceException(e: EventServiceException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            JsonMapper.toJsonString(
                HuiyikeResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            msg = "创建活动失败",
            data = "",
            err = e.message
        )))
    }
}