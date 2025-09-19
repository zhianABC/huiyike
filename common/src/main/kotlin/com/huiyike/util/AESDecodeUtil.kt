package com.huiyike.util

import com.huiyike.constant.HuiyikeConstant
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64

object AESDecodeUtil {
    fun aesDecryptECB(encrypted: String): String {
        var modifiedStr = encrypted.replace("*", "+")
        modifiedStr = modifiedStr.replace("-", "/")
        val padLength = 4 - (modifiedStr.length % 4)
        val paddedStr = if (padLength < 4) modifiedStr + "=".repeat(padLength) else modifiedStr
        val key = SecretKeySpec(HuiyikeConstant.AES_DECODE_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.decodeBase64(paddedStr)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun aesEncryptECB(data: String): String {
        val key = SecretKeySpec(HuiyikeConstant.AES_DECODE_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        // Encode to Base64 and replace characters to avoid URL issues
        // add = at the end to make it a multiple of 4
        val base64Encoded = Base64.encodeBase64String(encryptedBytes)
        val modifiedBase64 = base64Encoded.replace("+", "*").replace("/", "-")
        val padLength = 4 - (modifiedBase64.length % 4)
        val paddedBase64 = if (padLength < 4) modifiedBase64 + "=".repeat(padLength) else modifiedBase64
        return paddedBase64
    }
}