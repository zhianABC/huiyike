package com.huiyike.advice

import com.huiyike.exception.*
import com.huiyike.response.HuiyikeResponse
import com.huiyike.util.JsonMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserServiceAdvice {

    @ExceptionHandler(TencentServiceException::class)
    fun handleTencentServiceException(e: TencentServiceException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.SERVICE_UNAVAILABLE.value(),
            msg = "微信小程序登录失败",
            data = "",
            err = e.message
        )))
    }

    @ExceptionHandler(WechatLoginException::class)
    fun handleWechatLoginException(e: WechatLoginException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            msg = "微信小程序登录失败",
            data = "",
            err = e.message
        )))
    }

    @ExceptionHandler(UserInvalidException::class)
    fun handleUserInvalidException(e: UserInvalidException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            msg = "用户信息无效",
            data = "",
            err = e.message
        )))
    }

    @ExceptionHandler(AccountInvalidException::class)
    fun handleAccountInvalidException(e: AccountInvalidException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.FORBIDDEN.value(),
            msg = "用户登录失败",
            data = "",
            err = e.message
        )))
    }

    @ExceptionHandler(OSSException::class)
    fun handleOSSException(e: OSSException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.FAILED_DEPENDENCY.value(),
            msg = "OSS服务异常",
            data = "",
            err = e.message
        )))
    }
}