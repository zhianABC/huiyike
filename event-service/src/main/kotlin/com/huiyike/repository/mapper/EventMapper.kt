package com.huiyike.repository.mapper

import com.huiyike.repository.pojo.Event
import org.springframework.jdbc.core.RowMapper

class EventMapper : RowMapper<Event> {
    override fun mapRow(rs: java.sql.ResultSet, rowNum: Int): Event {
        return Event(
            eventId = rs.getString("event_id"),
            banner = rs.getString("banner"),
            fullName = rs.getString("full_name"),
            abbrName = rs.getString("abbr_name"),
            introduction = rs.getString("introduction"),
            detail = rs.getString("detail"),
            category = rs.getString("category"),
            industry = rs.getString("industry"),
            city = rs.getString("city"),
            country = rs.getString("country"),
            courtName = rs.getString("court_name"),
            courtLat = rs.getString("court_lat"),
            courtLng = rs.getString("court_lng"),
            detailAddr = rs.getString("detail_addr"),
            square = rs.getString("square"),
            startingDate = rs.getInt("starting_date"),
            endDate = rs.getInt("end_date"),
            createTime = rs.getLong("create_time"),
            updateTime = rs.getLong("update_time"),
            creator = rs.getString("creator"),
            status = rs.getInt("status"),
            remark = rs.getString("remark"),
            socialId = rs.getString("social_id")
        )
    }
}