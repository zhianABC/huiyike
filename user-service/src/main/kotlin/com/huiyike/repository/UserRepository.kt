package com.huiyike.repository

import com.huiyike.repository.mapper.PersonMapper
import com.huiyike.repository.mapper.UserMapper
import com.huiyike.repository.pojo.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun findPaginatedUsers(offset: Int, limit: Int): List<User> {
        val sql = "SELECT * FROM user LIMIT ? OFFSET ?"
        return jdbcTemplate.query(sql, UserMapper(), limit, offset)
    }

    fun saveBindPhoneUser(user: User) {
        val sql = """INSERT INTO user (user_id, cell_phone, create_date, update_date) 
                     VALUES (?, ?, ?, ?)""".trimIndent()
        jdbcTemplate.update(sql, user.userId, user.cellPhone, user.createDate, user.updateDate)
    }

    fun findUserById(userId: String): User? {
        val sql = "SELECT * FROM user WHERE user_id = ?"
        return jdbcTemplate.queryForObject(sql, UserMapper(), userId)
    }

    fun findUserByPhone(phone: String): User? {
        return try {
            val sql = "SELECT * FROM user WHERE cell_phone = ?"
            return jdbcTemplate.queryForObject(sql, UserMapper(), phone)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun updateUser(user: User) {
        val sql = """UPDATE user SET avatar = ?, real_name = ?, email = ?, cell_phone = ?, 
                     title = ?, company = ?, update_date = ? WHERE user_id = ?""".trimIndent()
        jdbcTemplate.update(sql, user.avatar, user.realName, user.email, user.cellPhone,
            user.title, user.company, user.updateDate, user.userId)
    }
}