package com.huiyike.ctrl

import com.huiyike.request.HuiyikeRequest
import com.huiyike.service.WechatService
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WechatController(
    private val wechatService: WechatService
) {
    @PostMapping("/user/v1/oauth/wechat/mp", consumes = ["multipart/form-data"])
    fun wechatMpLogin(@ModelAttribute huiyikeRequest: HuiyikeRequest): String {
        return wechatService.wechatMpLogin(huiyikeRequest)
    }
}