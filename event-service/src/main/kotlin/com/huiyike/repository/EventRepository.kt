package com.huiyike.repository

import com.huiyike.repository.mapper.EventMapper
import com.huiyike.repository.pojo.Event
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class EventRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun saveEvent(event: Event) {
        jdbcTemplate.update("INSERT INTO event (" +
                "    event_id, banner, full_name, abbr_name, introduction, detail, category, industry, city, country," +
                "    court_name, court_lat, court_lng, detail_addr, square, starting_date, end_date, create_time," +
                "    update_time, creator, status, remark" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            event.eventId, event.banner, event.fullName, event.abbrName, event.introduction, event.detail, event.category,
            event.industry, event.city, event.country, event.courtName, event.courtLat, event.courtLng, event.detailAddr,
            event.square, event.startingDate, event.endDate, event.createTime, event.updateTime,
            event.creator, event.status, event.remark)
    }

    fun getEventsByLimitOffsetAndMapper(limit: Int, offset: Int, appKey: String): List<Event> {
        return jdbcTemplate.query("SELECT * FROM event e inner join social s on e.social_id = s.social_id " +
                " inner join application a on a.social_id = s.social_id " +
                " LIMIT ? OFFSET ? where a.app_id = ?", EventMapper(), limit, offset, appKey)
    }

    fun updateEvent(event: Event) {
        jdbcTemplate.update("UPDATE event SET " +
                "banner = ?, full_name = ?, abbr_name = ?, introduction = ?, detail = ?, category = ?, industry = ?," +
                "city = ?, country = ?, court_name = ?, court_lat = ?, court_lng = ?, detail_addr = ?, square = ?," +
                "starting_date = ?, end_date = ?, update_time = ?, status = ?, remark = ? WHERE event_id = ?",
            event.banner, event.fullName, event.abbrName, event.introduction, event.detail, event.category,
            event.industry, event.city, event.country, event.courtName, event.courtLat, event.courtLng,
            event.detailAddr, event.square, event.startingDate, event.endDate, event.updateTime,
            event.status, event.remark, event.eventId)
    }
}