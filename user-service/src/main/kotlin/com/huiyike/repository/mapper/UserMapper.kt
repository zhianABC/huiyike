package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.User
import org.springframework.jdbc.core.RowMapper

class UserMapper : RowMapper<User> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): User {
        return User(userId = rs.getString("user_id"),
            avatar = rs.getString("avatar"),
            nickName = rs.getString("nick_name"),
            realName = rs.getString("real_name"),
            sex = rs.getInt("sex"),
            birthDate = rs.getInt("birth_date"),
            cellPhone = rs.getString("cell_phone"),
            email = rs.getString("email"),
            wechat = rs.getString("wechat"),
            qq = rs.getString("qq"),
            phone = rs.getString("phone"),
            country = rs.getString("country"),
            province = rs.getString("province"),
            city = rs.getString("city"),
            address = rs.getString("address"),
            postCode = rs.getInt("post_code"),
            source = rs.getString("source"),
            createDate = rs.getLong("create_date"),
            updateDate = rs.getLong("update_date"),
            creator = rs.getString("creator"),
            status = rs.getInt("status"),
            comment = rs.getString("comment"),
            title = rs.getString("title"),
            company = rs.getString("company"))
    }
}