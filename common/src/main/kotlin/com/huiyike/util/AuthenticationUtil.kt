package com.huiyike.util

import com.huiyike.exception.AuthenticationErrorException
import jakarta.servlet.ServletRequest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import org.slf4j.LoggerFactory

object AuthenticationUtil {
    private val logger = LoggerFactory.getLogger(AuthenticationUtil::class.java)

    fun isAuthenticated(appSecrets: Map<String, String>, p0: ServletRequest?): Boolean {
        val appKey = p0?.getParameter("app_key")
        val appUUID = p0?.getParameter("app_uuid")
        val requestUUID = p0?.getParameter("req_uuid")
        val timestamp = p0?.getParameter("timestamp")
        val signMethod = p0?.getParameter("sig_method")
        val sign = p0?.getParameter("signature")
        logger.info("app_key: $appKey, app_uuid: $appUUID, req_uuid: $requestUUID, timestamp: $timestamp, sig_method: $signMethod")
        checkParameterNullable(appKey, appUUID, requestUUID, timestamp, signMethod, sign)
        logger.info("app_key: $appKey, app_uuid: $appUUID, req_uuid: $requestUUID, timestamp: $timestamp, sig_method: $signMethod")
        checkAppKeyExist(appKey ?: "", appSecrets)
        logger.info("app_key: $appKey, app_uuid: $appUUID, req_uuid: $requestUUID, timestamp: $timestamp, sig_method: $signMethod")
        val signature = generateSignature(
            appKey ?: "", appUUID ?: "", requestUUID ?: "", timestamp ?: "", signMethod ?: "",
            appSecrets[appKey] ?: ""
        )
        logger.info("app_key: $appKey, app_uuid: $appUUID, req_uuid: $requestUUID, timestamp: $timestamp, sig_method: $signMethod, signature: $signature")
        return generateSignature(
            appKey ?: "", appUUID ?: "", requestUUID ?: "", timestamp ?: "", signMethod ?: "",
            appSecrets[appKey] ?: ""
        ) == sign
    }

    private fun checkParameterNullable(vararg params: String?) {
        params.find { s -> s.isNullOrEmpty() }?.let {
            throw AuthenticationErrorException("$it 不能为空")
        }
    }

    private fun checkAppKeyExist(appKey: String, appSecrets: Map<String, String>) {
        for ((key, value) in appSecrets) {
            logger.info("app_key: $key, app_secret: $value")
        }
        logger.info("检查 app_key: $appKey 是否存在于 appSecrets 中")
        if (!appSecrets.containsKey(appKey)) {
            logger.error("app_key $appKey 不存在")
            throw AuthenticationErrorException("app_key $appKey 不存在")
        }
    }

    private fun generateSignature(appKey: String, appUUID: String,
        requestUUID: String, timestamp: String, signMethod: String, appSecret: String): String {
        val authenStr = listOf("app_key=$appKey", "app_uuid=$appUUID", "req_uuid=$requestUUID",
            "sig_method=$signMethod", "timestamp=$timestamp"
        ).joinToString("&")
        return finalSign(hmacSign(authenStr, appSecret, getAlgorithm(signMethod)))
    }

    private fun getAlgorithm(signMethod: String): String {
        return when (signMethod) {
            "HMAC-SHA256" -> "HmacSHA256"
            "HMAC-SHA1" -> "HmacSHA1"
            else -> throw AuthenticationErrorException("不支持的签名方法: $signMethod")
        }
    }
    private fun hmacSign(data: String, key: String, algorithm: String): String {
        val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(secretKey)
        val hash = mac.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hash)
    }

    private fun finalSign(sourceSign: String) : String {
        return sourceSign.replace("+", "*").replace("/", "-").replace("=", "")
    }
}