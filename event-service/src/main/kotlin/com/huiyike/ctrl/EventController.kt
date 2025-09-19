package com.huiyike.ctrl

import com.huiyike.request.HuiyikeRequest
import com.huiyike.service.EventService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class EventController(
    private val eventService: EventService
) {

    @PostMapping("/social-event/v1/event", consumes = ["multipart/form-data"])
    fun createEvent(@ModelAttribute huiyikeRequest: HuiyikeRequest,
                    @RequestPart("fileavatar") file: MultipartFile?
    ): String {
        return eventService.createEvent(huiyikeRequest, file)
    }

    @GetMapping("/social-event/v1/events")
    fun getEvents(@RequestParam("data") data: String): String {
        return eventService.getEvents(data)
    }

    @PutMapping("/social-event/v1/events/{eventId}", consumes = ["multipart/form-data"])
    fun updateEvent(@PathVariable("eventId") eventId: String, @ModelAttribute huiyikeRequest: HuiyikeRequest,
                    @RequestPart("fileavatar") file: MultipartFile?
    ): String {
        return eventService.updateEvent(eventId, huiyikeRequest, file)
    }
}