package com.huiyike.ctrl

import com.huiyike.request.HuiyikeRequest
import com.huiyike.service.SocialService
import jakarta.websocket.server.PathParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class SocialController(
    private val socialService: SocialService
) {
    @PostMapping("/social-event/v1/social")
    fun createSocial(@ModelAttribute huiyikeRequest: HuiyikeRequest,
                     @RequestPart("fileavatar") file: MultipartFile?) : String {
        return socialService.createSocial(huiyikeRequest, file)
    }

    @GetMapping("/social-event/v1/socials")
    fun getSocials(@PathParam("data") data: String) : String {
        return socialService.getSocials(data)
    }

    @PutMapping("/social-event/v1/social/{socialId}", consumes = ["multipart/form-data"])
    fun updateSocial(@PathParam("socialId") socialId: String, @ModelAttribute huiyikeRequest: HuiyikeRequest,
                     @RequestPart("fileavatar") file: MultipartFile?) : String {
        return socialService.updateSocial(socialId, huiyikeRequest, file)
    }
}