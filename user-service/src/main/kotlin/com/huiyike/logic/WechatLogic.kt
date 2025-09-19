package com.huiyike.logic

import com.huiyike.constant.HuiyikeConstant
import com.huiyike.exception.TencentServiceException
import com.huiyike.repository.pojo.Person
import com.huiyike.response.HuiyikeResponse
import com.huiyike.response.WechatCodeResponseFromTencent
import com.huiyike.response.PersonOrUserIdTokenResponse
import com.huiyike.util.JsonMapper
import com.huiyike.util.JwtToken
import com.huiyike.util.UUIdGenerator
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class WechatLogic(
    private val restTemplate: RestTemplate
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WechatLogic::class.java)
    }

    fun jscode2session(code : String): WechatCodeResponseFromTencent? {
        try {
            val url = HuiyikeConstant.CODE_2_SESSION +
                    "?appid=${HuiyikeConstant.APP_ID}&secret=${HuiyikeConstant.APP_SECRET}&js_code=${code}&grant_type=authorization_code"
            val result = restTemplate.getForObject(url, String::class.java)
            return if (result.isNullOrBlank()) {
                throw TencentServiceException("获取微信小程序登录信息失败: 响应为空")
            } else {
                JsonMapper.fromJsonString(result, WechatCodeResponseFromTencent::class.java)
            }
        } catch (e: Exception) {
            logger.error("Error during jscode2session: ${e.message}")
            throw TencentServiceException("获取微信小程序登录信息失败", e)
        }
    }

    fun checkCode2SessionResponse(response: WechatCodeResponseFromTencent?) {
        if (response == null) {
            logger.error("微信小程序登录失败: 响应为空")
            throw TencentServiceException("获取微信小程序登录信息失败: 响应为空")
        }
        if (response.errcode != null && response.errcode != 0) {
            logger.error("微信小程序登录失败: ${response.errmsg}")
            throw TencentServiceException("获取微信小程序登录信息失败: ${response.errmsg}")
        }
    }

    fun generatePerson(response: WechatCodeResponseFromTencent): Person {
        return Person(
            openId = response.openid ?: "",
            sessionKey = response.session_key ?: "",
            unionId = response.unionid ?: "",
            personId = UUIdGenerator.generateId()
        )
    }
    
    fun generateResponse(person: Person) : String {
        return JsonMapper.toJsonString(HuiyikeResponse(
            code = 200,
            msg = "微信小程序登录成功",
            data = PersonOrUserIdTokenResponse(JwtToken.generateJwtToken(person.personId ?: ""))
        ))
    }

    fun checkExistingPerson(person: Person?) : Boolean {
        person?.let {
            return true
        }
        return false
    }
}