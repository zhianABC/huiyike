package com.huiyike.filter

import com.huiyike.constant.HuiyikeConstant
import com.huiyike.exception.AuthenticationErrorException
import com.huiyike.repository.ApplicationRepository
import com.huiyike.util.AuthenticationUtil
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class EventServiceFilter(
    private val applicationRepository: ApplicationRepository
) : Filter {
    private val logger = LoggerFactory.getLogger(EventServiceFilter::class.java)
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        MDC.put(HuiyikeConstant.MDC_APP_KEY, p0?.getParameter("app_key") ?: "")
        logger.info("The request is from ${p0?.remoteAddr}, method: ${p0?.serverName}, path: ${p0?.serverPort}}")
        // log request details
        logger.info("Request method: ${p0?.protocol}, remote address: ${p0?.remoteAddr}, server name: ${p0?.serverName}, server port: ${p0?.serverPort}")
        // log request parameters
        p0?.parameterMap?.forEach { (key, value) ->
            logger.info("Request parameter: $key = ${value.joinToString(", ")}")
        }
        try {
            if (!AuthenticationUtil.isAuthenticated(applicationRepository.getSecrets(), p0)) {
                val httpResp = p1 as? HttpServletResponse
                httpResp?.status = 401
                p1?.contentType = "application/json;charset=UTF-8"
                p1?.writer?.write("""{"code":401,"msg":"签名认证失败"}""")
                return
            }
        } catch (e: AuthenticationErrorException) {
            val httpResp = p1 as? HttpServletResponse
            httpResp?.status = 401
            p1?.contentType = "application/json;charset=UTF-8"
            p1?.writer?.write("""{"code":401,"msg":"签名认证失败"}""")
            logger.error("Authentication error: ${e.message}", e)
            return
        }
        p2?.doFilter(p0, p1)
    }
}