package com.huiyike.validator

import com.huiyike.exception.AccountInvalidException
import com.huiyike.exception.UserInvalidException
import com.huiyike.repository.pojo.Account
import com.huiyike.repository.pojo.User
import com.huiyike.request.UserUpdateRequest
import org.springframework.stereotype.Component

@Component
class UserValidator {
    fun validateUser(userId: String, userUpdateRequest: UserUpdateRequest, userByPhone: User?) {
        if (userUpdateRequest.realname == null) {
            throw UserInvalidException("真实姓名不能为空")
        }
        userByPhone?.let {
            if (it.userId != userId && it.cellPhone == userUpdateRequest.cellphone) {
                throw UserInvalidException("手机号已被其他用户绑定")
            }
        }
    }

    fun validateUserLogin(account: Account?) {
        if (account == null) {
            throw AccountInvalidException("用户不存在")
        }
    }
}