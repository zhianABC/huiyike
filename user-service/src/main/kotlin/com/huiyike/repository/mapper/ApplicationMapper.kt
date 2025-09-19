package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.Application
import org.springframework.jdbc.core.RowMapper

class ApplicationMapper : RowMapper<Application> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): Application {
        return Application(
            appId = rs.getString("app_id"),
            secret = rs.getString("secret"),
            socialId = rs.getString("social_id"),
            createDate = rs.getLong("create_date"),
            updateDate = rs.getLong("update_date")
        )
    }
}