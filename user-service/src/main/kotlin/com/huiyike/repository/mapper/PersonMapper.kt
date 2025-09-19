package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.Person
import com.huiyike.repository.pojo.User
import org.springframework.jdbc.core.RowMapper

class PersonMapper : RowMapper<Person> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): Person {
        return Person(personId = rs.getString("person_id"),
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
            unionId = rs.getString("union_id"),
            openId = rs.getString("open_id"),
            sessionKey = rs.getString("session_key"))
    }
}