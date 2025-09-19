package com.huiyike.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ApplicationRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun getSecrets(): Map<String, String> {
        val sql = "SELECT app_id, secret FROM application"
        return jdbcTemplate.query(sql) { rs, _ ->
            rs.getString("app_id") to rs.getString("secret")
        }.toMap()
    }

    fun getSocialIdByAppId(appId: String): String? {
        val sql = "SELECT social_id FROM application WHERE app_id = ?"
        return jdbcTemplate.queryForObject(sql, String::class.java, appId)
    }
}