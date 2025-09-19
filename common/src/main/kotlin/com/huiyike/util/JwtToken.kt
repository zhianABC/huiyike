package com.huiyike.util

import com.huiyike.constant.HuiyikeConstant
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.crypto.spec.SecretKeySpec

object JwtToken {
    fun generateJwtToken(userId: String): String {
        val now = LocalDateTime.now()
        val expiry = now.plusHours(24)
        val nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
        val expiryDate = Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant())
        val key = SecretKeySpec(HuiyikeConstant.JWT_SECRET.toByteArray(), SignatureAlgorithm.HS256.jcaName)
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(nowDate)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUserIdFromJwtToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(SecretKeySpec(HuiyikeConstant.JWT_SECRET.toByteArray(), SignatureAlgorithm.HS256.jcaName))
            .parseClaimsJws(token)
            .body
            .subject ?: throw IllegalArgumentException("Invalid JWT token")
    }
}