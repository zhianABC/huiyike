package com.huiyike.service

import com.huiyike.exception.SocialServiceException
import com.huiyike.logic.SocialLogic
import com.huiyike.oss.OSSClientService
import com.huiyike.repository.SocialRepository
import com.huiyike.request.HuiyikePathRequest
import com.huiyike.request.HuiyikeRequest
import com.huiyike.request.SocialRequest
import com.huiyike.util.AESDecodeUtil
import com.huiyike.util.JsonMapper
import com.huiyike.util.UUIdGenerator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class SocialService(
    private val socialLogic: SocialLogic,
    private val socialRepository: SocialRepository
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SocialService::class.java)

    fun createSocial(request: HuiyikeRequest, file: MultipartFile?) : String {
        try {
            val social = socialLogic.generateSocialFromRequest(request)
            file?.let {
                social.logo = OSSClientService.uploadFileToOSS(
                    "events/banner/${System.currentTimeMillis()}_${it.originalFilename}", it.inputStream)
            }
            socialRepository.saveSocial(social)
            return socialLogic.generateSocialResponse()
        } catch (e: Exception) {
            logger.error("创建组织失败: ${e.message}", e)
            throw SocialServiceException("创建组织失败: ${e.message}", e)
        }
    }

    fun getSocials(data: String) : String {
        try {
            val huiyikePathRequest = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(data), HuiyikePathRequest::class.java)
            val socials = socialRepository.getSocialsByMapper(huiyikePathRequest.num, huiyikePathRequest.start)
            return JsonMapper.toJsonString(
                socials
            )
        } catch (e: Exception) {
            logger.error("获取组织列表失败: ${e.message}", e)
            throw SocialServiceException("获取组织列表失败: ${e.message}", e)
        }
    }

    fun updateSocial(socialId: String, request: HuiyikeRequest, file: MultipartFile?) : String {
        try {
            val social = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(request.data), SocialRequest::class.java)
            file?.let {
                social.logo = OSSClientService.uploadFileToOSS(
                    "events/banner/${System.currentTimeMillis()}_${it.originalFilename}", it.inputStream)
            }
            social.socialId = socialId
            socialRepository.updateSocial(socialLogic.requestToPojo(social))
            return JsonMapper.toJsonString(
                socialLogic.generateSocialResponse()
            )
        } catch (e: Exception) {
            logger.error("更新组织失败: ${e.message}", e)
            throw SocialServiceException("更新组织失败: ${e.message}", e)
        }
    }
}