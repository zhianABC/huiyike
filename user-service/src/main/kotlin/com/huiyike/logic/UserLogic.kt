package com.huiyike.logic

import com.huiyike.exception.TokenInvalidException
import com.huiyike.repository.pojo.Person
import com.huiyike.repository.pojo.User
import com.huiyike.response.HuiyikeResponse
import com.huiyike.response.PersonOrUserIdTokenResponse
import com.huiyike.util.JsonMapper
import com.huiyike.util.JwtToken
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UserLogic {
    fun generateUsersResponse(users: List<User>): String {
        return JsonMapper.toJsonString(HuiyikeResponse(
                code = HttpStatus.OK.value(),
                msg = "用户列表获取成功",
                data = users.joinToString(separator = ", ") { it.toString() },
                err = null
            )
        )
    }

    fun getUserIdFromToken(authorization: String): String {
        return try {
            JwtToken.getUserIdFromJwtToken(authorization.removePrefix("Bearer ").trim())
        } catch (e: Exception) {
            throw TokenInvalidException("无效的授权令牌")
        }
    }

    fun generateProfileResponse(person: Person?): String {
        return if (person == null) {
            JsonMapper.toJsonString(HuiyikeResponse(
                code = HttpStatus.NOT_FOUND.value(),
                msg = "用户信息未找到",
                data = "",
                err = "用户信息未找到"
            ))
        } else {
            JsonMapper.toJsonString(HuiyikeResponse(
                code = HttpStatus.OK.value(),
                msg = "用户信息获取成功",
                data = generateDataByPerson(person),
                err = null
            ))
        }
    }

    private fun generateDataByPerson(person: Person) : Map<String, String?> {
        return mapOf(
            "personid" to person.personId,
            "realname" to person.realName,
            "cellphone" to person.cellPhone,
            "email" to person.email,
            "avatarurl" to person.avatar
        )
    }
    fun generateUserTokenWithMsgResponse(userOrPersonId: String, msg: String): String {
        return JsonMapper.toJsonString(HuiyikeResponse(
            code = HttpStatus.OK.value(),
            msg = msg,
            data = PersonOrUserIdTokenResponse(JwtToken.generateJwtToken(userOrPersonId)),
            err = null
        ))
    }
}