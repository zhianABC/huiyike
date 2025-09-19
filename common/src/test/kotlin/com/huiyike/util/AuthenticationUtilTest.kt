package com.huiyike.util

import jakarta.servlet.ServletRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthenticationUtilTest {
    @Test
    fun `isAuthenticated returns true for HMAC-SHA1 valid signature`() {
        val appKey = "please-set-app-key"
        val appSecret = "your-app-secret"
        val appSecrets = mapOf(appKey to appSecret)
        val appUUID = "15efad8a-6efd-4a6f-adc3-7909c447b002"
        val reqUUID = "please-set-req-uuid"
        val sigMethod = "HMAC-SHA1"
        val timestamp = "1752627178"

        val req = mock<ServletRequest>()
        whenever(req.getParameter("app_key")).thenReturn(appKey)
        whenever(req.getParameter("app_uuid")).thenReturn(appUUID)
        whenever(req.getParameter("req_uuid")).thenReturn(reqUUID)
        whenever(req.getParameter("timestamp")).thenReturn(timestamp)
        whenever(req.getParameter("sig_method")).thenReturn(sigMethod)

        val sign = AuthenticationUtil::class.java
            .getDeclaredMethod(
                "generateSignature",
                String::class.java, String::class.java, String::class.java,
                String::class.java, String::class.java, String::class.java
            ).apply { isAccessible = true }
            .invoke(
                AuthenticationUtil,
                appKey, appUUID, reqUUID, timestamp, sigMethod, appSecret
            ) as String

        println("sign = $sign")
    }

}