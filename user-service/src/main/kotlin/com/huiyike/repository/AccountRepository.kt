package com.huiyike.repository

import com.huiyike.repository.mapper.AccountMapper
import com.huiyike.repository.pojo.Account
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveAccount(account: Account) {
        val sql = """INSERT INTO account (user_id, user_name, salt, password, create_date, update_date) 
                     VALUES (?, ?, ?, ?, ?, ?)""".trimIndent()
        jdbcTemplate.update(sql, account.userId, account.username, account.salt, account.password,
            account.createDate, account.updateDate)
    }

    fun findAccountByUserName(username: String): Account? {
        val sql = "SELECT * FROM account WHERE user_name = ?"
        return try {
            jdbcTemplate.queryForObject(sql, AccountMapper(), username)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }
}