package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.properties.JiraProperties
import feign.Logger
import feign.auth.BasicAuthRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfiguration {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.BASIC;
    }

    @Bean
    fun basicAuthRequestInterceptor(jiraProperties: JiraProperties): BasicAuthRequestInterceptor {
        return BasicAuthRequestInterceptor(jiraProperties.auth.username, jiraProperties.auth.password)
    }
}