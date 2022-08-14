package com.basgeekball.jirasteward.client

import com.basgeekball.jirasteward.properties.JiraProperties
import feign.Logger
import feign.RequestInterceptor
import feign.auth.BasicAuthRequestInterceptor
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType

@Configuration
class FeignClientConfiguration {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.BASIC;
    }

    @Bean
    fun errorDecoder(): ErrorDecoder? {
        return JiraErrorDecoder()
    }

    @Bean
    fun requestInterceptor(): RequestInterceptor? {
        return RequestInterceptor { requestTemplate ->
            requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            requestTemplate.header("Accept", MediaType.APPLICATION_JSON_VALUE)
        }
    }

    @Bean
    fun basicAuthRequestInterceptor(jiraProperties: JiraProperties): BasicAuthRequestInterceptor {
        return BasicAuthRequestInterceptor(jiraProperties.auth.username, jiraProperties.auth.password)
    }
}