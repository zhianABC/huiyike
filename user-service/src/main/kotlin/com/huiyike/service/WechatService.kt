package com.huiyike.service

import com.huiyike.exception.TencentServiceException
import com.huiyike.exception.WechatLoginException
import com.huiyike.logic.WechatLogic
import com.huiyike.repository.PersonRepository
import com.huiyike.request.HuiyikeRequest
import com.huiyike.request.WechatCodeRequest
import com.huiyike.util.AESDecodeUtil
import com.huiyike.util.JsonMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WechatService(
    private val wechatLogic: WechatLogic,
    private val personRepository: PersonRepository
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WechatService::class.java)
    }
    fun wechatMpLogin(request: HuiyikeRequest): String {
        try {
            val code = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(request.data), WechatCodeRequest::class.java).code
            logger.info("微信小程序登录请求: code = $code")
            val response = wechatLogic.jscode2session(code)
            wechatLogic.checkCode2SessionResponse(response)
            val person = wechatLogic.generatePerson(response!!)
            if (wechatLogic.checkExistingPerson(personRepository.findPersonByUnionId(person.unionId ?: ""))) {
                logger.info("微信小程序登录成功: 已存在用户, unionId = ${person.unionId}")
                return wechatLogic.generateResponse(person)
            }
            personRepository.savePerson(person)
            return wechatLogic.generateResponse(person)
        }  catch (e: Exception) {
            when (e) {
                is TencentServiceException -> throw e
                else -> {
                    logger.error("微信小程序登录失败: ${e.message}", e)
                    throw WechatLoginException("微信小程序登录失败: ${e.message}", e)
                }
            }
        }
    }
}