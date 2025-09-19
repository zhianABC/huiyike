package com.huiyike.oss

import com.aliyun.oss.model.PutObjectRequest
import com.huiyike.constant.HuiyikeConstant
import com.huiyike.exception.OSSException
import java.io.InputStream

object OSSClientService {

    private val logger = org.slf4j.LoggerFactory.getLogger(OSSClientService::class.java)

    fun uploadFileToOSS(objectName: String, inputStream: InputStream): String {
        val ossClient = getOSSClient()
        try {
            val result = ossClient.putObject(PutObjectRequest(HuiyikeConstant.OSS_BUCKET_NAME, objectName,inputStream))
            logger.info("上传文件到OSS成功: ${result.response}")
        } catch (e: Exception) {
            logger.error("上传文件到OSS失败: ${e.message}", e)
            throw OSSException("上传文件到OSS失败: ${e.message}")
        } finally {
            ossClient.shutdown()
        }
        return "https://${HuiyikeConstant.OSS_BUCKET_NAME}.oss-cn-beijing.aliyuncs.com/$objectName"
    }

    private fun getOSSClient() : com.aliyun.oss.OSS {
        val endpoint = HuiyikeConstant.OSS_ENDPOINT
        val accessKeyId = HuiyikeConstant.OSS_ACCESS_KEY_ID
        val accessKeySecret = HuiyikeConstant.OSS_ACCESS_KEY_SECRET
        val conf = com.aliyun.oss.ClientBuilderConfiguration().apply {
            connectionTimeout = 10_000
            socketTimeout = 30_000
        }
        return com.aliyun.oss.OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf)
    }
}