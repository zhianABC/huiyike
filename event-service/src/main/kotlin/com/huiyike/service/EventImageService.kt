package com.huiyike.service

import com.huiyike.exception.OSSException
import com.huiyike.logic.EventImageLogic
import com.huiyike.oss.OSSClientService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Service
class EventImageService(
    private val eventImageLogic: EventImageLogic
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(EventImageService::class.java)
    fun exchangeImage(fileavatar: MultipartFile): String {
        try {
            val md5 = fileavatar.inputStream.use { it ->
                val digest = MessageDigest.getInstance("MD5")
                val buffer = ByteArray(8192)
                var read: Int
                while (it.read(buffer).also { read = it } != -1) {
                    digest.update(buffer, 0, read)
                }
                digest.digest().joinToString("") { b -> "%02x".format(b) }
            }
            val originalFilename = fileavatar.originalFilename ?: ""
            val extension = originalFilename.substringAfterLast('.', "")
            val uniqueFileName = if (extension.isNotEmpty()) {
                "events/album/${md5}.$extension"
            } else {
                "events/album/$md5"
            }
            var userAvatarUrl: String
            fileavatar.let {
                userAvatarUrl = OSSClientService.uploadFileToOSS(uniqueFileName, it.inputStream)
                logger.info("活动图片换url成功: $userAvatarUrl")
            }
            return eventImageLogic.generateExchangeImageResponse(userAvatarUrl)
        } catch (e: Exception) {
            logger.error("活动图片上传失败: ${e.message}", e)
            throw OSSException("活动图片上传失败: ${e.message}", e)
        }
    }
}