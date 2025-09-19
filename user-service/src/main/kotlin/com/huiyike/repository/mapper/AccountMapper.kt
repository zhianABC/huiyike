package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.Account
import org.springframework.jdbc.core.RowMapper

class AccountMapper : RowMapper<Account> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): Account {
        return Account(
            userId = rs.getString("user_id"),
            username = rs.getString("user_name"),
            salt = rs.getString("salt"),
            password = rs.getString("password"),
            createDate = rs.getLong("create_date"),
            updateDate = rs.getLong("update_date")
        )
    }
}