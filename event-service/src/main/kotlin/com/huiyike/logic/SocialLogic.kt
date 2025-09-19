package com.huiyike.logic

import com.huiyike.repository.pojo.Social
import com.huiyike.request.HuiyikeRequest
import com.huiyike.request.SocialRequest
import com.huiyike.response.HuiyikeResponse
import com.huiyike.util.AESDecodeUtil
import com.huiyike.util.JsonMapper
import com.huiyike.util.UUIdGenerator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class SocialLogic {
    fun requestToPojo(socialRequest: SocialRequest) : Social {
        return Social(
            socialId = socialRequest.socialId,
            logo = socialRequest.logo,
            fullName = socialRequest.fullName,
            abbrName = socialRequest.abbrName,
            introduction = socialRequest.introduction,
            industry = socialRequest.industry,
            creator = socialRequest.creator,
            createTime = socialRequest.createTime,
            updateTime = socialRequest.updateTime,
            status = socialRequest.status,
            remark = socialRequest.remark,
            category = socialRequest.category
        )
    }

    fun generateSocialResponse() : String {
        return JsonMapper.toJsonString(
            HuiyikeResponse(
            code = HttpStatus.OK.value(),
            msg = "创建组织成功",
            data = "",
            err = ""
        )
        )
    }

    fun generateSocialFromRequest(request: HuiyikeRequest) : Social {
        val social = JsonMapper.fromJsonString(AESDecodeUtil.aesDecryptECB(request.data), SocialRequest::class.java)
        social.socialId = UUIdGenerator.generateId()
        social.createTime = System.currentTimeMillis()
        social.updateTime = System.currentTimeMillis().toString()
        social.status = 1
        return requestToPojo(social)
    }
}