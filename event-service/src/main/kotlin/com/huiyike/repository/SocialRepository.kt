package com.huiyike.repository

import com.huiyike.repository.mapper.SocialMapper
import com.huiyike.repository.pojo.Social
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SocialRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun saveSocial(social: Social) {
        jdbcTemplate.update("INSERT INTO social (" +
                "    social_id, logo, full_name, abbr_name, introduction, industry, creator, create_time," +
                "    update_time, category, status, remark" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            social.socialId, social.logo, social.fullName, social.abbrName, social.introduction, social.industry,
            social.creator, social.createTime, social.updateTime, social.category, social.status, social.remark)
    }

    fun getSocialsByMapper(limit: Int, offset: Int): List<Social> {
        return jdbcTemplate.query("SELECT * FROM social LIMIT ? OFFSET ?", SocialMapper(), limit, offset)
    }

    fun updateSocial(social: Social) {
        jdbcTemplate.update("UPDATE social SET " +
                "logo = ?, full_name = ?, abbr_name = ?, introduction = ?, industry = ?, update_time = ?," +
                "status = ?, remark = ?, category = ? WHERE social_id = ?",
            social.logo, social.fullName, social.abbrName, social.introduction, social.industry,
            social.updateTime, social.status, social.remark, social.category, social.socialId)
    }
}