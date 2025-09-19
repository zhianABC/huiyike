package com.huiyike.util

import org.junit.jupiter.api.Test

class JwtTokenTest {
    @Test
    fun `is token valid`() {
        val token = JwtToken.generateJwtToken("4f4425dc-5fba-4ebe-b39d-99b4755833b4")
        val token2 = JwtToken.generateJwtToken("oEGx_jtHZwkXadV6mzC9DP5l7ajo")
        val userId = JwtToken.getUserIdFromJwtToken(token)
        val userId2 = JwtToken.getUserIdFromJwtToken(token)
        println("userId = $userId")
        println("token = $token")
        println("userId = $userId")
        println("token2 = $token2")
    }
}