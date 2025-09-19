package com.huiyike.service

import com.huiyike.constant.HuiyikeConstant
import com.huiyike.exception.UserServiceException
import com.huiyike.repository.BindPhoneRepository
import com.huiyike.repository.pojo.BindPhone
import com.huiyike.response.YunpianSMSResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap

@Service
class YunpianSMSService(
    private val restTemplate: RestTemplate,
    private val bindPhoneRepository: BindPhoneRepository
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(YunpianSMSService::class.java)
    fun sendSMS(phone: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val randomCode = randomCode()
        val response = restTemplate.postForObject(HuiyikeConstant.YUNPIAN_URL,
            HttpEntity(parameters(phone, randomCode), headers), YunpianSMSResponse::class.java)
        if (response == null || response.code != 0) {
            logger.error("Yunpian SMS API error: ${response?.msg ?: "Unknown error"}")
            throw UserServiceException("Failed to send SMS: ${response?.msg ?: "Unknown error"}")
        } else {
            bindPhoneRepository.saveBindPhone(BindPhone(phone, randomCode, System.currentTimeMillis()))
        }
    }

    fun parameters(phone: String, randomCode: String): LinkedMultiValueMap<String, String> {
        val params = LinkedMultiValueMap<String, String>()
        params["apikey"] = HuiyikeConstant.YUNPIAN_API_KEY
        params["text"] = String.format(HuiyikeConstant.YUNPIAN_TEMPLATE, randomCode)
        params["mobile"] = phone
        return params
    }

    fun randomCode(): String {
        val code = StringBuilder()
        for (i in 0 until 4) {
            code.append((0..9).random())
        }
        return code.toString()
    }
}