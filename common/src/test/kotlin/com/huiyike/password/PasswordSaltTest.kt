package com.huiyike.password

import org.junit.jupiter.api.Test

class PasswordSaltTest {
    @Test
    fun testGenerateSalt() {
        val salt = PasswordSalt.generateSalt()
        assert(salt.isNotEmpty()) {
            "Generated salt should not be empty"
        }
        println("Generated salt: $salt")
        println("Salt length: ${salt.length}")
    }
}