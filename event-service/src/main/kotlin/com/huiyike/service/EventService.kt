package com.huiyike.service

import com.huiyike.constant.HuiyikeConstant
import com.huiyike.exception.EventServiceException
import com.huiyike.exception.SocialServiceException
import com.huiyike.logic.EventLogic
import com.huiyike.oss.OSSClientService
import com.huiyike.repository.ApplicationRepository
import com.huiyike.repository.EventRepository
import com.huiyike.request.EventRequest
import com.huiyike.request.HuiyikePathRequest
import com.huiyike.request.HuiyikeRequest
import com.huiyike.util.AESDecodeUtil
import com.huiyike.util.JsonMapper
import com.huiyike.util.UUIdGenerator
import org.slf4j.MDC
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EventService(
    private val eventLogic: EventLogic,
    private val eventRepository: EventRepository,
    private val applicationRepository: ApplicationRepository
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(EventService::class.java)

    fun createEvent(request: HuiyikeRequest, file: MultipartFile?): String {
        val event = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(request.data), EventRequest::class.java)
        try {
            file?.let {
                event.banner = OSSClientService.uploadFileToOSS(
                    "events/banner/${System.currentTimeMillis()}_${it.originalFilename}", it.inputStream)
            }
            event.eventId = UUIdGenerator.generateId()
            event.socialId = applicationRepository.getSocialIdByAppId(MDC.get(HuiyikeConstant.MDC_APP_KEY))
            eventRepository.saveEvent(eventLogic.generateEventByRequest(event))
            return eventLogic.generateEventResponse()
        } catch (e: Exception) {
            logger.error("创建活动失败: ${e.message}", e)
            throw EventServiceException("创建活动失败: ${e.message}", e)
        }
    }

    fun getEvents(data: String) : String {
        try {
            val huiyikePathRequest = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(data), HuiyikePathRequest::class.java)
            val events = eventRepository.getEventsByLimitOffsetAndMapper(huiyikePathRequest.num, huiyikePathRequest.start,
                MDC.get(HuiyikeConstant.MDC_APP_KEY))
            return JsonMapper.toJsonString(
                events
            )
        } catch (e: Exception) {
            logger.error("获取组织列表失败: ${e.message}", e)
            throw SocialServiceException("获取组织列表失败: ${e.message}", e)
        }
    }

    fun updateEvent(eventId: String, request: HuiyikeRequest, file: MultipartFile?) : String {
        val event = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(request.data), EventRequest::class.java)
        try {
            file?.let {
                event.banner = OSSClientService.uploadFileToOSS(
                    "events/banner/${System.currentTimeMillis()}_${it.originalFilename}", it.inputStream)
            }
            event.eventId = eventId
            eventRepository.updateEvent(eventLogic.generateEventByRequest(event))
            return JsonMapper.toJsonString(
                eventLogic.generateEventResponse()
            )
        } catch (e: Exception) {
            logger.error("更新活动失败: ${e.message}", e)
            throw EventServiceException("更新活动失败: ${e.message}", e)
        }
    }
}