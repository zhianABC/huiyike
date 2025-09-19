package com.huiyike.ctrl

import com.huiyike.request.HuiyikeRequest
import com.huiyike.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/user/get")
    fun queryUsers(): String {
        return userService.queryUsers()
    }

    @GetMapping("/user/v1/me/profile")
    fun getUserProfile(@RequestHeader("Authorization") authorization: String): String {
        return userService.queryUser(authorization)
    }

    @PutMapping("/user/v1/me/profile", consumes = ["multipart/form-data"])
    fun updateUserProfile(@RequestHeader("Authorization") authorization: String,
                          @ModelAttribute huiyikeRequest: HuiyikeRequest,
                          @RequestPart("fileavatar") file: MultipartFile?
    ): String {
        return userService.updateUserProfile(authorization, huiyikeRequest, file)
    }

    @PostMapping("/user/v1/otp/bind", consumes = ["multipart/form-data"])
    fun bindPhoneNumber(@ModelAttribute huiyikeRequest: HuiyikeRequest): String {
        return userService.bindPhoneNumber(huiyikeRequest)
    }

    @PostMapping("/user/v1/otp/password", consumes = ["multipart/form-data"])
    fun setPassword(@RequestHeader("Authorization") authorization: String,
                    @ModelAttribute huiyikeRequest: HuiyikeRequest): String {
        return userService.setPassword(authorization, huiyikeRequest)
    }

    @GetMapping("/user/v1/user/login")
    fun userLogin(@RequestParam("data") data: String): String {
        return userService.userLogin(data)
    }
}