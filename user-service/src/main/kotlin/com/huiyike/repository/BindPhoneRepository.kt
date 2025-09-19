package com.huiyike.repository

import com.huiyike.repository.pojo.BindPhone
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BindPhoneRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveBindPhone(bindPhone: BindPhone) {
        val sql = """INSERT INTO bind_phone (phone, code, create_time) VALUES (?, ?, ?)""".trimIndent()
        jdbcTemplate.update(sql, bindPhone.phone, bindPhone.code, bindPhone.createTime)
    }
}