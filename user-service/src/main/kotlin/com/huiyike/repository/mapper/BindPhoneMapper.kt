package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.BindPhone
import org.springframework.jdbc.core.RowMapper

class BindPhoneMapper : RowMapper<BindPhone> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): BindPhone {
        return BindPhone(
            phone = rs.getString("phone"),
            code = rs.getString("code"),
            createTime = rs.getLong("create_time")
        )
    }
}