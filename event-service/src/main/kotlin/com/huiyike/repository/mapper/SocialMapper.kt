package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.Social
import org.springframework.jdbc.core.RowMapper

class SocialMapper : RowMapper<Social> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): Social {
        return Social(
            socialId = rs.getString("social_id"),
            logo = rs.getString("logo"),
            fullName = rs.getString("full_name"),
            abbrName = rs.getString("abbr_name"),
            introduction = rs.getString("introduction"),
            industry = rs.getString("industry"),
            creator = rs.getString("creator"),
            createTime = rs.getLong("create_time"),
            updateTime = rs.getString("update_time"),
            status = rs.getInt("status"),
            remark = rs.getString("remark"),
            category = rs.getString("category")
        )
    }
}