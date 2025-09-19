package com.huiyike.repository

import com.huiyike.repository.mapper.PersonMapper
import com.huiyike.repository.pojo.Person
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class PersonRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun savePerson(person: Person) {
        jdbcTemplate.update(
            "INSERT INTO person (person_id, open_id, union_id, session_key) VALUES (?, ?, ?, ?)",
            person.personId, person.openId, person.unionId, person.sessionKey
        )
    }

    fun findPersonByUnionId(unionId: String): Person? {
        return try {
            jdbcTemplate.queryForObject(
                "SELECT * FROM person WHERE union_id = ?", PersonMapper(), unionId
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun findPersonByPersonId(personId: String): Person? {
        return try {
            jdbcTemplate.queryForObject(
                "SELECT * FROM person WHERE person_id = ?", PersonMapper(), personId
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun saveBindPhonePerson(person: Person) {
        jdbcTemplate.update(
            "INSERT INTO person (person_id, cell_phone, create_date, update_date) VALUES (?, ?, ?, ?)",
            person.personId, person.cellPhone, person.createDate, person.updateDate
        )
    }
}