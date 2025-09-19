package com.huiyike.config

import com.huiyike.filter.EventServiceFilter
import com.huiyike.repository.ApplicationRepository
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {
    @Bean
    fun userServiceFilter(applicationRepository: ApplicationRepository): FilterRegistrationBean<EventServiceFilter> {
        val registration = FilterRegistrationBean(EventServiceFilter(applicationRepository))
        registration.addUrlPatterns("/*")
        registration.order = 1
        return registration
    }
}