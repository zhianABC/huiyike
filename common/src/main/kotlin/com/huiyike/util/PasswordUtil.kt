package com.huiyike.util

import java.security.SecureRandom
import java.security.MessageDigest
import org.apache.commons.codec.binary.Base64

object PasswordUtil {
    fun generateSalt(length: Int = 16): String {
        val salt = ByteArray(length)
        SecureRandom().nextBytes(salt)
        return Base64.encodeBase64String(salt)
    }

    fun hashPassword(password: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val input = (password + salt).toByteArray()
        val hash = md.digest(input)
        return Base64.encodeBase64String(hash)
    }

    fun isPasswordValid(password: String, salt: String, hashedPassword: String): Boolean {
        val hashedInput = hashPassword(password, salt)
        return hashedInput == hashedPassword
    }
}