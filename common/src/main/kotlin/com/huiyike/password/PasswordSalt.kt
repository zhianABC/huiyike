package com.huiyike.password

object PasswordSalt {
    fun generateSalt(): String {
        val salt = ByteArray(4)
        java.security.SecureRandom().nextBytes(salt)
        return salt.joinToString("") {
            String.format("%02x", it)
        }
    }
}