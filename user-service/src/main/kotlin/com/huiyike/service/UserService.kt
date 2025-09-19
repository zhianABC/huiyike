package com.huiyike.service

import com.huiyike.exception.AccountInvalidException
import com.huiyike.exception.TokenInvalidException
import com.huiyike.exception.UserInvalidException
import com.huiyike.exception.UserServiceException
import com.huiyike.logic.UserLogic
import com.huiyike.oss.OSSClientService
import com.huiyike.repository.AccountRepository
import com.huiyike.repository.PersonRepository
import com.huiyike.repository.UserRepository
import com.huiyike.repository.pojo.Account
import com.huiyike.repository.pojo.Person
import com.huiyike.repository.pojo.User
import com.huiyike.request.HuiyikeRequest
import com.huiyike.request.UserBindPhoneRequest
import com.huiyike.request.UserPasswordRequest
import com.huiyike.request.UserUpdateRequest
import com.huiyike.request.UserLoginRequest
import com.huiyike.util.AESDecodeUtil
import com.huiyike.util.JsonMapper
import com.huiyike.util.PasswordUtil
import com.huiyike.util.UUIdGenerator
import com.huiyike.validator.UserValidator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val personRepository: PersonRepository,
    private val userLogic: UserLogic,
    private val userValidator: UserValidator,
    private val yunpianSMSService: YunpianSMSService,
    private val accountRepository: AccountRepository
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(UserService::class.java)

    fun queryUsers(): String {
        userRepository.findPaginatedUsers(0, 10)
        return userLogic.generateUsersResponse(
            userRepository.findPaginatedUsers(0, 10))
    }

    fun queryUser(authorization: String): String {
        try {
            return userLogic.generateProfileResponse(
                personRepository.findPersonByPersonId(
                    userLogic.getUserIdFromToken(authorization)))
        } catch (e: Exception) {
            when (e) {
                is TokenInvalidException -> throw e
                else -> {
                    throw UserServiceException("查询用户信息错误", e)
                }
            }
        }
    }

    fun updateUserProfile(authorization: String, huiyikeRequest: HuiyikeRequest, fileavatar: MultipartFile?): String {
        try {
            val userId = userLogic.getUserIdFromToken(authorization)
            val request = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(huiyikeRequest.data), UserUpdateRequest::class.java)
            var userByPhone: User? = null
            if (request.cellphone != null) {
                userByPhone = userRepository.findUserByPhone(request.cellphone)
            }
            userValidator.validateUser(userId, request, userByPhone)
            var userAvatarUrl = ""
            fileavatar?.let {
                val fileName = "avatars/${userId}/${System.currentTimeMillis()}_${it.originalFilename}"
                userAvatarUrl = OSSClientService.uploadFileToOSS(fileName, it.inputStream)
                logger.info("用户头像上传成功: $userAvatarUrl")
            }
            userRepository.updateUser(
                User(userId = userId, realName = request.realname ?: "", cellPhone = request.cellphone?: "",
                    avatar = userAvatarUrl, updateDate = System.currentTimeMillis()))
            return userLogic.generateUserTokenWithMsgResponse(userId, "用户信息更新成功")
        } catch (e: Exception) {
            when (e) {
                is TokenInvalidException, is UserInvalidException -> throw e
                else -> {
                    throw UserServiceException("更新用户信息错误", e)
                }
            }
        }
    }

    fun bindPhoneNumber(huiyikeRequest: HuiyikeRequest): String {
        try {
            val phone = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(huiyikeRequest.data), UserBindPhoneRequest::class.java).cellphone
            yunpianSMSService.sendSMS(phone)
            val userOrPersonId = UUIdGenerator.generateId()
            val currentTime = System.currentTimeMillis()
            personRepository.saveBindPhonePerson(Person(personId = userOrPersonId, cellPhone = phone, createDate = currentTime, updateDate = currentTime))
            userRepository.saveBindPhoneUser(User(userId = userOrPersonId, cellPhone = phone, createDate = currentTime, updateDate = currentTime))
            return userLogic.generateUserTokenWithMsgResponse(userOrPersonId, "用户手机绑定成功")
        } catch (e: Exception) {
            throw UserServiceException("用户绑定手机号发生错误", e)
        }
    }

    fun setPassword(authorization: String, huiyikeRequest: HuiyikeRequest): String {
        try {
            val userId = userLogic.getUserIdFromToken(authorization)
            val request = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(huiyikeRequest.data), UserPasswordRequest::class.java)
            val salt = PasswordUtil.generateSalt()
            val hashedPassword = PasswordUtil.hashPassword(request.password, salt)
            val currentTime = System.currentTimeMillis()
            accountRepository.saveAccount(Account(userId = userId, username = request.username,
                salt = salt, password = hashedPassword, createDate = currentTime, updateDate = currentTime))
            return userLogic.generateUserTokenWithMsgResponse(userId, "用户密码设置成功")
        } catch (e: Exception) {
            when (e) {
                is TokenInvalidException -> throw e
                else -> {
                    throw UserServiceException("用户设置密码发生错误", e)
                }
            }
        }
    }

    fun userLogin(data: String): String {
        try {
            val request = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(data), UserLoginRequest::class.java)
            val account = accountRepository.findAccountByUserName(request.username)
            userValidator.validateUserLogin(account)
            if (!PasswordUtil.isPasswordValid(request.password, account!!.salt, account.password)) {
                throw AccountInvalidException("密码错误")
            }
            return userLogic.generateUserTokenWithMsgResponse(account.userId, "用户登录成功")
        } catch (e: Exception) {
            when (e) {
                is TokenInvalidException -> throw e
                else -> {
                    throw UserServiceException("用户登录发生错误", e)
                }
            }
        }
    }
}