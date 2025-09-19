package com.huiyike.ctrl

import com.huiyike.service.EventImageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EventImageExchangeController(
    private val eventImageService: EventImageService
) {
    @PostMapping("/social-event/v1/event/image-exchange", consumes = ["multipart/form-data"])
    fun exchangeEventImage(@RequestPart("fileavatar") file: MultipartFile): String {
        return eventImageService.exchangeImage(file)
    }
}